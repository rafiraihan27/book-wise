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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public void setRackNumber(String rackNumber) {
        this.rackNumber = rackNumber;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }

    public void setCanBorrow(boolean canBorrow) {
        this.canBorrow = canBorrow;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getQuota() {
        return quota;
    }

    public String getRackNumber() {
        return rackNumber;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getLanguage() {
        return language;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public BigDecimal getLateFee() {
        return lateFee;
    }

    public boolean isCanBorrow() {
        return canBorrow;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
