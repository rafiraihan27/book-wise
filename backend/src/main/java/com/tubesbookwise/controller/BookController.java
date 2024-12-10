package com.tubesbookwise.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Operation(summary = "get book", description = "Retrieve book")
    @GetMapping("/")
    public ResponseEntity<?> getBook() {
        // Contoh respons sederhana
        return ResponseEntity.ok().body(
                Map.of("message", "Halo")
        );
    }
}
