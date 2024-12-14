package com.tubesbookwise.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "books", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title"}),   // Add unique constraint for title
        @UniqueConstraint(columnNames = {"isbn"})    // Add unique constraint for ISBN
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Use UUID strategy for primary key
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)") // Ensure CHAR(36) for UUID
    private UUID id;

    @Column(name = "title", nullable = false, length = 255)  // Ensures that title is a required field with unique constraint
    private String title;

    @Column(name = "author", nullable = false, length = 255)  // Ensures that author is a required field
    private String author;

    @Column(name = "category", length = 255)
    private String category;

    @Column(name = "year")
    private int year;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image", length = 255)
    private String image;

    @Column(name = "quota")
    private int quota;

    @Column(name = "rack_number", length = 255)
    private String rackNumber;

    @Column(name = "isbn", nullable = false, unique = true, length = 255)  // Ensures that ISBN is a required field with unique constraint
    private String isbn;

    @Column(name = "language", length = 255)
    private String language;

    @Column(name = "available_copies")
    private int availableCopies;

    @Column(name = "late_fee", precision = 10, scale = 2)  // Matches DECIMAL(10,2)
    private BigDecimal lateFee;

    @Column(name = "can_borrow", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean canBorrow = true;  // Set default value

    @Column(name = "rating", precision = 3, scale = 2)  // Matches DECIMAL(3,2)
    private BigDecimal rating;

    // One-to-many relationship with Review entity
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

}
