package com.tubesbookwise.controller;

import com.tubesbookwise.Models.Notification;
import com.tubesbookwise.Models.User;
import com.tubesbookwise.Service.NotificationsService;
import com.tubesbookwise.Service.User.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationsService notificationsService;

    @Autowired
    public NotificationController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    //    GET http://localhost:8080/api/notifications?userId=1
    @Operation(summary = "Ambil daftar notifikasi by userId", description = "Mengambil daftar notifikasi berdasarkan userId")
    @GetMapping()
    public ResponseEntity<?> getNotif(
            @RequestParam(value = "userId", required = true) String userId
    ) {
        // Fetch notifications for the given userId
        List<Notification> notifications = notificationsService.getNotificationsByUserId(userId);
        return ResponseEntity.ok().body(notifications);
    }

    //    PUT http://localhost:8080/api/notifications?notifId=1
    @Operation(summary = "Update status notifikasi by notifId", description = "Mengupdate status notifikasi read = true")
    @PutMapping()
    public ResponseEntity<?> updateStatusNotif(
            @RequestParam(value = "notifId", required = true) String notifId
    ) {
        try {
            Notification updatedNotification = notificationsService.markAsRead(notifId);
            return ResponseEntity.ok().body(updatedNotification);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //    POST http://localhost:8080/api/notifications
    @Operation(summary = "Tambah notifikasi baru", description = "Menambahkan notifikasi baru untuk user tertentu")
    @PostMapping()
    public ResponseEntity<?> createNotif(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "message", required = true) String message,
            @RequestParam(value = "type", required = true) Notification.NotificationType type
    ) {
        try {
            User user = notificationsService.getUserById(userId); // Assumes a method to fetch User by ID exists in service
            Notification newNotification = notificationsService.addNotification(user, title, message, type);
            return ResponseEntity.ok().body(newNotification);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //    DELETE http://localhost:8080/api/notifications/{notifId}
    @Operation(summary = "Hapus notifikasi", description = "Menghapus notifikasi berdasarkan notifId")
    @DeleteMapping("/{notifId}")
    public ResponseEntity<?> deleteNotif(
            @PathVariable String notifId
    ) {
        try {
            notificationsService.deleteNotification(notifId);
            return ResponseEntity.ok().body(Map.of("message", "Notification deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
