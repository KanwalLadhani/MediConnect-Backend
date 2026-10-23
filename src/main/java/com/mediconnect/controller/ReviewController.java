package com.mediconnect.controller;

import com.mediconnect.dto.ReviewCreateRequest;
import com.mediconnect.model.OrderReview;
import com.mediconnect.model.User;
import com.mediconnect.model.ServiceOrder;
import com.mediconnect.repository.OrderReviewRepository;
import com.mediconnect.repository.UserRepository;
import com.mediconnect.repository.ServiceOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired private OrderReviewRepository orderReviewRepository;
    @Autowired private ServiceOrderRepository serviceOrderRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<OrderReview>> getReviewsForWorker(@PathVariable Long workerId) {
        return ResponseEntity.ok(orderReviewRepository.findByWorkerIdOrderByCreatedAtDesc(workerId));
    }

    @GetMapping
    public ResponseEntity<List<OrderReview>> getAllReviews() {
        return ResponseEntity.ok(orderReviewRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewCreateRequest request) {
        ServiceOrder order = serviceOrderRepository.findById(request.getOrderId()).orElse(null);
        if (order == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Order not found"));
        }
        String patientName = "Patient";
        if (request.getPatientId() != null) {
            User patient = userRepository.findById(request.getPatientId()).orElse(null);
            if (patient != null) patientName = patient.getFullName();
        }
        OrderReview review = new OrderReview(
            request.getOrderId(),
            order.getWorker() != null ? order.getWorker().getId() : null,
            request.getPatientId(),
            patientName,
            request.getRating(),
            request.getComment()
        );
        orderReviewRepository.save(review);
        return ResponseEntity.ok(review);
    }
}
