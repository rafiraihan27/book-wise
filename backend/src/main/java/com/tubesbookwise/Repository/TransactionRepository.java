package com.tubesbookwise.Repository;

import com.tubesbookwise.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    // Find transaction by invoiceCode
    Optional<Transaction> findByInvoiceCode(String invoiceCode);

    // Find transactions by status and type
    List<Transaction> findByStatusAndType(String status, String type);
}
