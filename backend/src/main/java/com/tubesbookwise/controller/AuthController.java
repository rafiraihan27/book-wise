package com.tubesbookwise.controller;

import com.tubesbookwise.Models.User;
import com.tubesbookwise.Repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // POST http://localhost:8080/api/auth/login
    @Operation(summary = "Login user", description = "Autentikasi user dengan email dan password")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String password) {

        // Find the user by email
        User user = (User) userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the password matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        // Exclude password from response and return user data
        user.setPassword(null);  // Remove sensitive data (password) from the response

        return ResponseEntity.ok(user);  // Respond with user data (excluding password)
    }

    // POST http://localhost:8080/api/auth/login/admin
    @Operation(summary = "Login admin", description = "Autentikasi admin dengan email dan password")
    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdmin(
            @RequestParam String email,
            @RequestParam String password) {

        // Find the user by email
        User user = (User) userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Admin not found"));

        // Check if the password matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        // Check if the user is an admin
        if (user.getRole() != User.Role.admin) {
            return ResponseEntity.status(403).body("Unauthorized: Admin access only");
        }

        // Exclude password from response and return user data
        user.setPassword(null);  // Remove sensitive data (password) from the response

        return ResponseEntity.ok(user);  // Respond with user data (excluding password)
    }
}
