package com.tubesbookwise.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    //    GET http://localhost:8080/api/books?search="Dunia Sophie"&category="fiksi"&years=1991
    @Operation(summary = "Ambil daftar buku", description = "Mengambil daftar buku berdasarkan parameter query")
    @GetMapping()
    public ResponseEntity<?> getBook(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "years", required = false) String years
            ) {
        return ResponseEntity.ok().body(
                Map.of("message", search +" "+category+" "+years)
        );
    }

    //    GET http://localhost:8080/api/books/1
    @Operation(summary = "Ambil daftar buku by ID buku", description = "Mengambil daftar buku berdasarkan ID buku")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookByID(
            @PathVariable String id
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", id)
        );
    }

    //    GET http://localhost:8080/api/books/recommended?max=4
    @Operation(summary = "Ambil daftar buku rekomendasi", description = "Mengambil daftar rekomendasi buku berdasar max buku")
    @GetMapping("/recommended")
    public ResponseEntity<?> getBookRecommendation(
            @RequestParam(value = "max", required = false) Integer max
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", max)
        );
    }

    //    POST http://localhost:8080/api/books
    @Operation(summary = "Tambah data buku", description = "Menambah data buku")
    @PostMapping()
    public ResponseEntity<?> postBook(
            @Valid @RequestBody String bookName
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", bookName)
        );
    }

    //    PUT http://localhost:8080/api/books/:id
    @Operation(summary = "Update data buku", description = "Update/edit data buku")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable String id,
            @Valid @RequestBody String bookName
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", id+" "+bookName)
        );
    }

    //    DELETE http://localhost:8080/api/books/:id
    @Operation(summary = "Delete data buku", description = "Menghapus data buku berdasarkan id buku")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(
            @PathVariable String id
    ) {
        return ResponseEntity.ok().body(
                Map.of("message", id)
        );
    }
}
