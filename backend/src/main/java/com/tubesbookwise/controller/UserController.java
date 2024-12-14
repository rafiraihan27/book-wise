package com.tubesbookwise.controller;

import com.tubesbookwise.dto.User.*;
import com.tubesbookwise.Models.User;
import com.tubesbookwise.Repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Mengambil semua data pengguna", description = "Mengambil semua data pengguna berdasarkan parameter role (opsional)")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(@RequestParam(value = "role", required = false) String role) {
        List<User> users;
        if (role != null && !role.isEmpty()) {
            users = userRepository.findAllByRole(User.Role.valueOf(role));
        } else {
            users = userRepository.findAll();
        }

        List<UserResponseDTO> responses = users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getName(),
                        user.getRole(),
                        user.getPhone(),
                        user.getNim(),
                        user.getNip(),
                        user.getYear()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }


    //    GET http://localhost:8080/api/users/1
    @Operation(summary = "Mengambil data user berdasarkan ID-nya.", description = "Mengambil data user berdasarkan ID-nya.")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "ID of the user to retrieve", required = true) @PathVariable String id) {
        Optional<User> user = userRepository.findById(id);
        UserResponseDTO response = new UserResponseDTO(
                user.get().getId(),
                user.get().getEmail(),
                user.get().getName(),
                user.get().getRole(),
                user.get().getPhone(),
                user.get().getNim(),
                user.get().getNip(),
                user.get().getYear()
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update user data by id", description = "TAMBAHKAN ROLE [admin, student, lecturer]")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUserFields(
            @PathVariable String id,
            @RequestBody AllUserRequestDTO allUserRequestDTO) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Find the user by ID
            Optional<User> existingUserOptional = userRepository.findById(id);
            if (existingUserOptional.isEmpty()) {
                response.put("status", 404);
                response.put("message", "User not found");
                return ResponseEntity.status(404).body(response);
            }

            User existingUser = existingUserOptional.get();

            // Check for duplicate entries (e.g., email, phone, etc.)
            if (userRepository.existsByEmail(allUserRequestDTO.getEmail()) &&
                    !existingUser.getEmail().equals(allUserRequestDTO.getEmail())) {
                response.put("status", 400);
                response.put("message", "Email is already in use");
                return ResponseEntity.status(400).body(response);
            }
            if (userRepository.existsByPhone(allUserRequestDTO.getPhone()) &&
                    !existingUser.getPhone().equals(allUserRequestDTO.getPhone())) {
                response.put("status", 400);
                response.put("message", "Phone number is already in use");
                return ResponseEntity.status(400).body(response);
            }
            if (userRepository.existsByNim(allUserRequestDTO.getNim()) &&
                    !existingUser.getNim().equals(allUserRequestDTO.getNim())) {
                response.put("status", 400);
                response.put("message", "NIM is already in use");
                return ResponseEntity.status(400).body(response);
            }

            // Update fields only if they are different
            if (allUserRequestDTO.getEmail() != null && !allUserRequestDTO.getEmail().equals(existingUser.getEmail())) {
                existingUser.setEmail(allUserRequestDTO.getEmail());
            }
            if (allUserRequestDTO.getName() != null && !allUserRequestDTO.getName().equals(existingUser.getName())) {
                existingUser.setName(allUserRequestDTO.getName());
            }
            if (allUserRequestDTO.getPassword() != null && !allUserRequestDTO.getPassword().equals(existingUser.getPassword())) {
                existingUser.setPassword(allUserRequestDTO.getPassword());
            }
            if (allUserRequestDTO.getPhone() != null && !allUserRequestDTO.getPhone().equals(existingUser.getPhone())) {
                existingUser.setPhone(allUserRequestDTO.getPhone());
            }
            if (allUserRequestDTO.getRole() != null) {
                existingUser.setRole(User.Role.valueOf(allUserRequestDTO.getRole().toString()));
            }
            if (allUserRequestDTO.getNim() != null && !allUserRequestDTO.getNim().equals(existingUser.getNim())) {
                existingUser.setNim(allUserRequestDTO.getNim());
            }
            if (allUserRequestDTO.getNip() != null && !allUserRequestDTO.getNip().equals(existingUser.getNip())) {
                existingUser.setNip(allUserRequestDTO.getNip());
            }
            if (allUserRequestDTO.getYear() != null && !allUserRequestDTO.getYear().equals(existingUser.getYear())) {
                existingUser.setYear(allUserRequestDTO.getYear());
            }

            // Save the updated user
            userRepository.save(existingUser);

            response.put("status", 200);
            response.put("message", "User updated successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", 500);
            response.put("error", "Internal Server Error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // POST http://localhost:8080/api/users/register/student
    @Operation(summary = "Register a student", description = "Register a user with the role of student")
    @PostMapping("/register/student")
    public ResponseEntity<User> registerStudent(@Valid @RequestBody StudentRequestDTO studentDTO) {
        User user = new User();
        user.setEmail(studentDTO.getEmail());
        user.setPassword(studentDTO.getPassword());
        user.setName(studentDTO.getName());
        user.setRole(User.Role.student);
        user.setPhone(studentDTO.getPhone());
        user.setNim(studentDTO.getNim());
        user.setYear(studentDTO.getYear());

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    // POST http://localhost:8080/api/users/register/lecturer
    @Operation(summary = "Register a lecturer", description = "Register a user with the role of lecturer")
    @PostMapping("/register/lecturer")
    public ResponseEntity<User> registerLecturer(@Valid @RequestBody LecturerRequestDTO lecturerDTO) {
        User user = new User();
        user.setEmail(lecturerDTO.getEmail());
        user.setPassword(lecturerDTO.getPassword());
        user.setName(lecturerDTO.getName());
        user.setRole(User.Role.lecturer);
        user.setPhone(lecturerDTO.getPhone());
        user.setNip(lecturerDTO.getNip());

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    // POST http://localhost:8080/api/users/register/admin
    @Operation(summary = "Register an admin", description = "Register a user with the role of admin")
    @PostMapping("/register/admin")
    public ResponseEntity<User> registerAdmin(@Valid @RequestBody UserRequestDTO adminDTO) {
        User user = new User();
        user.setEmail(adminDTO.getEmail());
        user.setPassword(adminDTO.getPassword());
        user.setName(adminDTO.getName());
        user.setRole(User.Role.admin);
        user.setPhone(adminDTO.getPhone());

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    //    DELETE http://localhost:8080/api/users/1
    @Operation(summary = "Delete user", description = "Delete a user by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true) @PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            userRepository.deleteById(id);
            response.put("status", "success");
            response.put("message", "User deleted successfully");
            response.put("userId", id);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to delete user");
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

}
