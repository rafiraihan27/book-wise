package com.tubesbookwise.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistic")
public class StatisticDashboardController {

    //    GET http://localhost:8080/api/statistic
    @Operation(summary = "Ambil data statistik", description = "Mengambil data statistik dari database")
    @GetMapping()
    public ResponseEntity<?> getStatistics() {
        return ResponseEntity.ok().body(
                Map.of(
                        "message", "Data statistik berhasil diambil",
                        "data", Map.of(
                                "totalBook", 100,
                                "totalUser", 50,
                                "totalTransaction", 20,
                                "averageReview", 3.9
                        )
                )
        );
    }
}
