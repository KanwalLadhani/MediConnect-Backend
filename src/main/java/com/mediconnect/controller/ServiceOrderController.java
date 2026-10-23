package com.mediconnect.controller;

import com.mediconnect.dto.OrderCreateRequest;
import com.mediconnect.dto.OrderStatusUpdateRequest;
import com.mediconnect.model.*;
import com.mediconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class ServiceOrderController {

    @Autowired private ServiceOrderRepository serviceOrderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private HealthWorkerRepository healthWorkerRepository;
    @Autowired private ServiceCategoryRepository serviceCategoryRepository;

    @GetMapping
    public ResponseEntity<List<ServiceOrder>> getAllOrders() {
        return ResponseEntity.ok(serviceOrderRepository.findAllByOrderByCreatedAtDesc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return serviceOrderRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ServiceOrder>> getOrdersByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(serviceOrderRepository.findByPatientIdOrderByCreatedAtDesc(patientId));
    }

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<List<ServiceOrder>> getOrdersByWorker(@PathVariable Long workerId) {
        return ResponseEntity.ok(serviceOrderRepository.findByWorkerIdOrderByCreatedAtDesc(workerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ServiceOrder>> getOrdersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(serviceOrderRepository.findByStatusOrderByCreatedAtDesc(status.toUpperCase()));
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest request) {
        User patient = userRepository.findById(request.getPatientId()).orElse(null);
        if (patient == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Patient not found"));
        }
        ServiceCategory category = serviceCategoryRepository.findById(request.getServiceCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Service category not found"));
        }

        ServiceOrder order = new ServiceOrder();
        order.setPatient(patient);
        order.setServiceCategory(category);
        order.setSymptomsDescription(request.getSymptomsDescription());
        order.setPrescriptionNotes(request.getPrescriptionNotes());
        order.setUrgencyLevel(request.getUrgencyLevel() != null ? request.getUrgencyLevel() : "ROUTINE");
        order.setStatus("REQUESTED");
        order.setPatientAddress(request.getPatientAddress() != null ? request.getPatientAddress() : patient.getAddress());
        order.setPatientCity(request.getPatientCity() != null ? request.getPatientCity() : patient.getCity());
        order.setPatientPhone(request.getPatientPhone() != null ? request.getPatientPhone() : patient.getPhone());

        Double amount = request.getCustomAmountPkr() != null ? request.getCustomAmountPkr() : category.getSuggestedPricePkr();
        order.setTotalAmountPkr(amount);
        order.setPlatformFeePkr(amount * 0.15);
        order.setWorkerEarningPkr(amount * 0.85);

        if (request.getWorkerId() != null) {
            HealthWorker worker = healthWorkerRepository.findById(request.getWorkerId()).orElse(null);
            if (worker != null) {
                order.setWorker(worker);
                order.setStatus("ACCEPTED");
                order.setEtaMinutes(20);
            }
        }

        serviceOrderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    @RequestMapping(value = "/{id}/status", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusUpdateRequest request) {
        return serviceOrderRepository.findById(id).map(order -> {
            if (request.getStatus() != null) {
                order.setStatus(request.getStatus());
            }
            if (request.getEtaMinutes() != null) {
                order.setEtaMinutes(request.getEtaMinutes());
            }
            if (request.getWorkerLatitude() != null) {
                order.setWorkerLatitude(request.getWorkerLatitude());
            }
            if (request.getWorkerLongitude() != null) {
                order.setWorkerLongitude(request.getWorkerLongitude());
            }
            if ("COMPLETED".equals(request.getStatus())) {
                order.setCompletedAt(LocalDateTime.now());
                order.setEtaMinutes(0);
            }
            serviceOrderRepository.save(order);
            return ResponseEntity.ok(order);
        }).orElse(ResponseEntity.notFound().build());
    }
}
