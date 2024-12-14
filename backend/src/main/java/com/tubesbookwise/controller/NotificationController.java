package com.tubesbookwise.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    //    GET http://localhost:8080/api/notifications?userId=1
    @Operation(summary = "Ambil daftar notifikasi by userId", description = "Mengambil daftar notifikasi berdasarkan userId")
    @GetMapping()
    public ResponseEntity<?> getNotif(
            @RequestParam(value = "userId", required = true) String userId
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", userId)
        );
    }

    //    PUT http://localhost:8080/api/notifications
    @Operation(summary = "update status notifikasi by userId", description = "mengupdate status notifikasi read = [true, false]")
    @PutMapping()
    public ResponseEntity<?> updateStatusNotif(
            @Valid @RequestBody String notifId
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", notifId)
        );
    }
}
