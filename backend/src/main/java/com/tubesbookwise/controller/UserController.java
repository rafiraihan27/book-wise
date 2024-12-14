package com.tubesbookwise.controller;

import com.tubesbookwise.dto.User.LecturerRequestDTO;
import com.tubesbookwise.dto.User.StudentRequestDTO;
import com.tubesbookwise.dto.User.UserRequestDTO;
import com.tubesbookwise.dto.User.UserResponseDTO;
import com.tubesbookwise.model.User;
import com.tubesbookwise.repository.User.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    GET http://localhost:8080/api/users?role=admin
    @Operation(summary = "Mengambil semua data pengguna", description = "Mengambil semua data pengguna berdasarkan parameter role (opsional)")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(@RequestParam(value = "role", required = false) String role) {
        List<User> users = userRepository.findAll(role);
        users.forEach(System.out::println);
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
        User user = userRepository.findById(id);
        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getPhone(),
                user.getNim(),
                user.getNip(),
                user.getYear()
        );
        return ResponseEntity.ok(response);
    }

    // PUT http://localhost:8080/api/users/{id}
    @Operation(summary = "Update user data by id", description = "TAMBAHKAN ROLE [admin, student, lecturer]")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        // Find the existing user by ID
        Optional<User> existingUserOptional = Optional.ofNullable(userRepository.findById(id));
        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User existingUser = existingUserOptional.get();

        // Handle different roles by using the DTOs
        switch (User.Role.valueOf(userRequestDTO.getRole().toLowerCase())) {
            case student:
                StudentRequestDTO studentDTO = (StudentRequestDTO) userRequestDTO;
                existingUser.setNim(studentDTO.getNim());
                existingUser.setYear(studentDTO.getYear());
                break;
            case lecturer:
                LecturerRequestDTO lecturerDTO = (LecturerRequestDTO) userRequestDTO;
                existingUser.setNip(lecturerDTO.getNip());
                break;
            case admin:
                // No additional fields for Admin
                break;
            default:
                return ResponseEntity.status(400).body("Invalid role");
        }

        // Update common fields for all roles
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setPassword(userRequestDTO.getPassword());
        existingUser.setName(userRequestDTO.getName());
        existingUser.setRole(User.Role.valueOf(userRequestDTO.getRole().toLowerCase()));
        existingUser.setPhone(userRequestDTO.getPhone());

        // Save the updated user
        userRepository.update(existingUser);
        return ResponseEntity.ok("User updated successfully");
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
    public ResponseEntity<String> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true) @PathVariable String id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
