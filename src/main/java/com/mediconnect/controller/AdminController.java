package com.mediconnect.controller;

import com.mediconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private UserRepository userRepository;
    @Autowired private HealthWorkerRepository healthWorkerRepository;
    @Autowired private ServiceOrderRepository serviceOrderRepository;
    @Autowired private OrderReviewRepository orderReviewRepository;
    @Autowired private WalletTransactionRepository walletTransactionRepository;
    @Autowired private ServiceCategoryRepository serviceCategoryRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalPatients = userRepository.findByRole("PATIENT").size();
        long totalWorkers = healthWorkerRepository.count();
        long totalOrders = serviceOrderRepository.count();
        long totalReviews = orderReviewRepository.count();
        long totalCategories = serviceCategoryRepository.count();

        return ResponseEntity.ok(Map.of(
            "totalUsers", totalUsers,
            "totalPatients", totalPatients,
            "totalWorkers", totalWorkers,
            "totalOrders", totalOrders,
            "totalReviews", totalReviews,
            "totalServiceCategories", totalCategories
        ));
    }

    @PatchMapping("/workers/{id}/verify")
    public ResponseEntity<?> verifyWorker(@PathVariable Long id, @RequestParam String status) {
        return healthWorkerRepository.findById(id).map(worker -> {
            worker.setVerificationStatus(status.toUpperCase());
            healthWorkerRepository.save(worker);
            return ResponseEntity.ok(worker);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/users/{id}/toggle")
    public ResponseEntity<?> toggleUserActive(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            // Not a real field but can be added
            return ResponseEntity.ok(user);
        }).orElse(ResponseEntity.notFound().build());
    }
}
