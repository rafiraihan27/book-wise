package com.tubesbookwise.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "notifications")  // Maps the class to the "notifications" table in the database
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)  // Foreign key to the "users" table
    private User user;

    @Column(name = "title", nullable = false)  // Title of the notification
    private String title;

    @Column(name = "message", columnDefinition = "TEXT", nullable = false)  // Message content of the notification
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)  // Enum for notification type, mapped as a string
    private NotificationType type;

    @Column(name = "date", nullable = false)  // Timestamp when the notification was created
    private LocalDateTime date;

    @Column(name = "`read`", nullable = false)
    private boolean read = false;  // Default value is false

    // Enum for Notification Type
    public enum NotificationType {
        INFO, REMINDER, ALERT
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}