package com.tubesbookwise.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "transactions")  // Maps the class to the "transactions" table in the database
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentEvidence() {
        return paymentEvidence;
    }

    public void setPaymentEvidence(String paymentEvidence) {
        this.paymentEvidence = paymentEvidence;
    }

    public List<TransactionItem> getItems() {
        return items;
    }

    public void setItems(List<TransactionItem> items) {
        this.items = items;
    }
}