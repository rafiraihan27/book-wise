package com.tubesbookwise.controller;

import com.tubesbookwise.Service.TransactionService;
import com.tubesbookwise.dto.TransactionRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //    POST http://localhost:8080/api/transactions
    //    payload:
    //    {
    //        "userId": "f82db5c6-50ad-4da3-b89e-71245fab66bd",
    //            "items": [
    //        {
    //            "id": "121c2a2b-fdb1-4f63-a2a7-2a7db33d3c61",
    //                "title": "Pride and Prejudice",
    //                "image": "https://images-na.ssl-images-amazon.com/images/I/81Uwwlm7fJL.jpg",
    //                "author": "Jane Austen"
    //        }
    //  ],
    //        "totalFee": 1000,
    //            "paymentMethod": "bank",
    //            "paymentEvidence": "https://github.com/gagassurya19/book-wise/blob/main/frontend/app/(pages)/(user)/settings/layout.tsx",
    //            "dateFrom": "2024-12-21T17:00:00.000Z",
    //            "dateTo": "2024-12-23T17:00:00.000Z"
    //    }
    @Operation(summary = "Tambah data transaksi", description = "Menambah data transaksi")
    @PostMapping()
    public ResponseEntity<?> postTransaction(
            @Valid @RequestBody TransactionRequest transactionRequest
    ) {
        try {
            // Call service to create the transaction
            var transaction = transactionService.createTransaction(transactionRequest);
            return ResponseEntity.ok().body(
                    Map.of("message", "Transaction successfully created", "data", transaction)
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Failed to create transaction", "error", e.getMessage()));
        }
    }

    @Operation(summary = "Ambil data invoice transaksi berdasarkan invoiceCode", description = "Mengambil daftar transaksi berdasarkan [invoiceCode=INV-20241211-0001]")
    @GetMapping("/invoice")
    public ResponseEntity<?> getTransactionByInvoiceCode(
            @RequestParam(value = "invoiceCode", required = true) String invoiceCode
    ) {
        // Call the service method to get transaction data by invoiceCode
        Map<String, Object> response = transactionService.getTransactionByInvoiceCode(invoiceCode);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Ambil data transaksi berdasarkan status & type (optional)", description = "Mengambil data transaksi berdasarkan status & type")
    @GetMapping()
    public ResponseEntity<?> getTransactionWithStatusType(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "userId", required = false) String userId
    ) {
        // Call the service method to get transactions with status and type
        return ResponseEntity.ok().body(transactionService.getTransactionsWithFilter(search, status, type, userId));
    }

    @PutMapping
    @Operation(summary = "Perbarui status transaksi berdasarkan kode invoice",
            description = "Memperbarui status transaksi dengan kode invoice dan status baru.")
    public ResponseEntity<?> updateTransactionStatus(
            @RequestParam(value = "invoiceCode", required = true) String invoiceCode,
            @RequestParam(value = "status", required = true) String status
    ) {
        try {
            transactionService.updateTransactionStatus(invoiceCode, status);
            return ResponseEntity.ok().body(Map.of("message", "Status transaksi berhasil diperbarui"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Gagal memperbarui status transaksi"));
        }
    }

}
