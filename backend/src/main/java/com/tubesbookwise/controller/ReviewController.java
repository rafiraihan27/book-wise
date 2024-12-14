package com.tubesbookwise.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    //    GET http://localhost:8080/api/reviews?bookId={idbook}
    @Operation(summary = "Ambil daftar review by bookId", description = "Mengambil daftar review buku berdasarkan bookId")
    @GetMapping()
    public ResponseEntity<?> getNotif(
            @RequestParam(value = "bookId", required = true) String bookId
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", bookId)
        );
    }

    //    POST http://localhost:8080/api/reviews
    @Operation(summary = "Tambah review", description = "Menambah review untuk buku")
    @PostMapping()
    public ResponseEntity<?> postBook(
            @Valid @RequestBody String bookId
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", bookId)
        );
    }
}
