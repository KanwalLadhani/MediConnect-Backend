package com.mediconnect.controller;

import com.mediconnect.model.ServiceCategory;
import com.mediconnect.repository.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceCategoryController {

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @GetMapping
    public ResponseEntity<List<ServiceCategory>> getAllServices() {
        return ResponseEntity.ok(serviceCategoryRepository.findByIsActiveTrue());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getServiceById(@PathVariable Long id) {
        return serviceCategoryRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
