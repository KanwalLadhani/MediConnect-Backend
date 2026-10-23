package com.mediconnect.controller;

import com.mediconnect.dto.AuthRequest;
import com.mediconnect.dto.AuthResponse;
import com.mediconnect.model.HealthWorker;
import com.mediconnect.model.User;
import com.mediconnect.repository.HealthWorkerRepository;
import com.mediconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HealthWorkerRepository healthWorkerRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "User not found"));
        }
        User user = userOpt.get();
        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid password"));
        }
        // Fake JWT token for hackathon
        String token = "mc_" + Base64.getEncoder().encodeToString(
            (user.getId() + ":" + user.getRole() + ":" + System.currentTimeMillis()).getBytes()
        );
        HealthWorker workerProfile = null;
        if ("HEALTH_WORKER".equals(user.getRole())) {
            workerProfile = healthWorkerRepository.findByUserId(user.getId()).orElse(null);
        }
        return ResponseEntity.ok(new AuthResponse(token, user, workerProfile));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole() != null ? request.getRole() : "PATIENT");
        user.setCity(request.getCity());
        user.setAddress(request.getAddress());
        userRepository.save(user);

        String token = "mc_" + Base64.getEncoder().encodeToString(
            (user.getId() + ":" + user.getRole() + ":" + System.currentTimeMillis()).getBytes()
        );
        return ResponseEntity.ok(new AuthResponse(token, user, null));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(userRepository.findByRole(role.toUpperCase()));
    }
}
