package com.tubesbookwise.dto.Review;

import com.tubesbookwise.Models.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class ReviewDTO {

    // Getters and Setters
    private UUID id;
    private String bookTitle;
    private String authorName;
    private LocalDateTime date;
    private double rating;
    private String content;

    // Constructor for the DTO
    public ReviewDTO(UUID id, String bookTitle, String authorName, LocalDateTime date, double rating, String content) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.date = date;
        this.rating = rating;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
