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

    @ManyToOne(fetch = FetchType.EAGER)  // Change LAZY to EAGER
    @JoinColumn(name = "book_id", nullable = false)  // Foreign key to the "books" table
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)  // Change LAZY to EAGER
    @JoinColumn(name = "author_id", nullable = false)  // Foreign key to the "users" table
    private User author;

    @Column(name = "date", nullable = false)  // Timestamp when the review was written
    private LocalDateTime date;

    @Column(name = "rating", nullable = false)  // Rating given in the review
    private double rating;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)  // Content of the review (text type)
    private String content;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
