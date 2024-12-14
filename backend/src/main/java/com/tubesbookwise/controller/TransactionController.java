package com.tubesbookwise.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    //    GET http://localhost:8080/api/transactions/invoice?invoiceCode=INV-20241211-0001
    @Operation(summary = "Ambil data invoice transaksi berdasarkan invoiceCode", description = "Mengambil daftar transaksi berdasarkan [invoiceCode=INV-20241211-0001]")
    @GetMapping("/invoice")
    public ResponseEntity<?> getTransactionByInvoiceCode(
            @RequestParam(value = "invoiceCode", required = true) String invoiceCode
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", invoiceCode)
        );
    }

    //    GET http://localhost:8080/api/transactions?status=pending&type=borrow
    @Operation(summary = "Ambil data transaksi berdasarkan berdasarkan status & type (optional)", description = "Mengambil data transaksi berdasarkan status & type")
    @GetMapping()
    public ResponseEntity<?> getTransactionWithStatusType(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "type", required = false) String type
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", status+" "+type)
        );
    }

    //    POST http://localhost:8080/api/transactions
    @Operation(summary = "Tambah data transaksi", description = "Menambah data transaksi")
    @PostMapping()
    public ResponseEntity<?> postTransaction(
            @Valid @RequestBody String userId

    ) {
        return ResponseEntity.ok().body(
                Map.of("message", userId)
        );
    }

    //    PUT http://localhost:8080/api/transactions?invoiceCode=INV-20241211-0001&status=approved
    @Operation(summary = "Update status transaksi", description = "Update status transaksi")
    @PutMapping("")
    public ResponseEntity<?> updateStatusTransaction(
            @RequestParam(value = "invoiceCode", required = true) String invoiceCode,
            @RequestParam(value = "status", required = true) String status
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", invoiceCode+" "+status)
        );
    }

}
