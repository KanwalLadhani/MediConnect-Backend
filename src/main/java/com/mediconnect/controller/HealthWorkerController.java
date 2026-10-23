package com.mediconnect.controller;

import com.mediconnect.model.HealthWorker;
import com.mediconnect.repository.HealthWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
public class HealthWorkerController {

    @Autowired
    private HealthWorkerRepository healthWorkerRepository;

    @GetMapping
    public ResponseEntity<List<HealthWorker>> getAllWorkers() {
        return ResponseEntity.ok(healthWorkerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkerById(@PathVariable Long id) {
        return healthWorkerRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    public ResponseEntity<List<HealthWorker>> getAvailableWorkers() {
        return ResponseEntity.ok(healthWorkerRepository.findByVerificationStatusAndIsAvailable("APPROVED", true));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<HealthWorker>> getWorkersByCity(@PathVariable String city) {
        return ResponseEntity.ok(healthWorkerRepository.findByCityIgnoreCase(city));
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<?> toggleAvailability(@PathVariable Long id, @RequestParam Boolean available) {
        return healthWorkerRepository.findById(id).map(worker -> {
            worker.setIsAvailable(available);
            healthWorkerRepository.save(worker);
            return ResponseEntity.ok(worker);
        }).orElse(ResponseEntity.notFound().build());
    }
}
