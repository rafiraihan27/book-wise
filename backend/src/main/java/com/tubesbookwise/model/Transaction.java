package com.tubesbookwise.model;

import java.time.LocalDate;
import java.util.List;

public class Transaction {
    private String id;
    private String invoiceCode;
    private DateRange dateRange;
    private double totalFee;
    private TransactionStatus status; // Enum for status
    private TransactionType type; // Enum for type
    private String paymentMethod;
    private String paymentEvidence;
    private List<TransactionItem> items; // List of book items
    private User user;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Enum for Transaction Status
    public enum TransactionStatus {
        PENDING, APPROVED, DECLINED, OVERDUE
    }

    // Enum for Transaction Type
    public enum TransactionType {
        BORROW, RETURN
    }

    // Inner class for Date Range
    public static class DateRange {
        private LocalDate from;
        private LocalDate to;

        public LocalDate getFrom() {
            return from;
        }

        public void setFrom(LocalDate from) {
            this.from = from;
        }

        public LocalDate getTo() {
            return to;
        }

        public void setTo(LocalDate to) {
            this.to = to;
        }
    }
}
