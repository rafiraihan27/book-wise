package com.tubesbookwise.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "reviews")  // Maps the class to the "reviews" table in the database
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Auto generation strategy for the primary key
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;  // Primary key for the review

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)  // Foreign key to the "books" table
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)  // Foreign key to the "users" table
    private User author;

    @Column(name = "date", nullable = false)  // Timestamp when the review was written
    private LocalDateTime date;

    @Column(name = "rating", nullable = false)  // Rating given in the review
    private double rating;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)  // Content of the review (text type)
    private String content;
}
