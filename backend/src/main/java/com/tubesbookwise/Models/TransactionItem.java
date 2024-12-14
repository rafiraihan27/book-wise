package com.tubesbookwise.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "transaction_items")
public class TransactionItem {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "late_fee", precision = 10, scale = 2)
    private BigDecimal lateFee;  // Use BigDecimal for precision and scale

    @Column(name = "title", nullable = false)
    private String title;

}
