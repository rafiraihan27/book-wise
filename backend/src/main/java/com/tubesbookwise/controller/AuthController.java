package com.tubesbookwise.controller;

import com.tubesbookwise.model.User;
import com.tubesbookwise.repository.User.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    POST http://localhost:8080/api/auth/login
    @Operation(summary = "Login user", description = "Autentikasi user dengan email dan password")
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "Email user dibutuhkan" , required = true) @RequestParam String email,
            @Parameter(description = "Password user dibutuhkan", required = true) @RequestParam String password) {
        User user = userRepository.authenticate(email, password);
        return ResponseEntity.ok("Login berhasil untuk user: " + user.getEmail());
    }

    //    POST http://localhost:8080/api/auth/login/admin
    @Operation(summary = "Login admin", description = "Autentikasi admin dengan email dan password")
    @PostMapping("/login/admin")
    public ResponseEntity<String> loginAdmin(
            @Parameter(description = "Email admin dibutuhkan", required = true) @RequestParam String email,
            @Parameter(description = "Password user dibutuhkan", required = true) @RequestParam String password) {
        User user = userRepository.authenticate(email, password);
        return ResponseEntity.ok("Login berhasil untuk user: " + user.getEmail());
    }
}
