package com.tubesbookwise.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "notifications")  // Maps the class to the "notifications" table in the database
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // Auto generation strategy for the primary key
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;  // Primary key for the notification

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(name = "read", nullable = false)
    private boolean read = false;  // Default value is false

    // Enum for Notification Type
    public enum NotificationType {
        INFO, REMINDER, ALERT
    }
}