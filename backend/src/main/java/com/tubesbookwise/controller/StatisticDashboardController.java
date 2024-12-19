package com.tubesbookwise.controller;

import com.tubesbookwise.Service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@RestController
@RequestMapping("/api/statistic")
public class StatisticDashboardController {
    @Autowired
        private StatisticService statisticservice;
    //    GET http://localhost:8080/api/statistic
    @Operation(summary = "Ambil data statistik", description = "Mengambil data statistik dari database")
    @GetMapping()
    public ResponseEntity<?> getStatistics() {
        return ResponseEntity.ok().body(
                Map.of(
                        "message", "Data statistik berhasil diambil",
                        "data", Map.of(
                                "totalBook", statisticservice.getStatisticBook(),
                                "totalUser", statisticservice.getJumlahUserNow(),
                                "totalTransaction", 20,
                                "averageReview", 3.9,
                                "totalReview", statisticservice.getJumlahReview()
                        )
                )
        );
    }
}
