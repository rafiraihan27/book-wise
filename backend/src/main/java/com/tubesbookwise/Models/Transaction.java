package com.tubesbookwise.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "transactions")  // Maps the class to the "transactions" table in the database
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Auto generation strategy for the primary key
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;  // Primary key for the transaction

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)  // Foreign key to the "users" table
    private User user;

    @Column(name = "invoice_code", nullable = false, unique = true)  // Unique invoice code
    private String invoiceCode;

    @Column(name = "date_from", nullable = false)  // Start date of the transaction
    private LocalDate dateFrom;

    @Column(name = "date_to", nullable = false)  // End date of the transaction
    private LocalDate dateTo;

    @Column(name = "total_fee", nullable = false)  // Total fee for the transaction
    private double totalFee;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)  // Status of the transaction (enum)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)  // Type of the transaction (enum)
    private TransactionType type;

    @Column(name = "payment_method")  // Payment method used in the transaction
    private String paymentMethod;

    @Column(name = "payment_evidence")  // Evidence of the payment made
    private String paymentEvidence;

    // One-to-many relationship with TransactionItem entity
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionItem> items;

    // Enum for Transaction Status
    public enum TransactionStatus {
        PENDING, APPROVED, DECLINED, OVERDUE
    }

    // Enum for Transaction Type
    public enum TransactionType {
        BORROW, RETURN
    }
}