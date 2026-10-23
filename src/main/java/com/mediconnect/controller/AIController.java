package com.mediconnect.controller;

import com.mediconnect.dto.AIPrescriptionRequest;
import com.mediconnect.dto.AIPrescriptionResponse;
import com.mediconnect.dto.AITriageRequest;
import com.mediconnect.dto.AITriageResponse;
import com.mediconnect.service.AITriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AITriageService aiTriageService;

    @PostMapping("/triage")
    public ResponseEntity<AITriageResponse> triageSymptoms(@RequestBody AITriageRequest request) {
        return ResponseEntity.ok(aiTriageService.triageSymptoms(request));
    }

    @PostMapping("/prescription")
    public ResponseEntity<AIPrescriptionResponse> analyzePrescription(@RequestBody AIPrescriptionRequest request) {
        return ResponseEntity.ok(aiTriageService.analyzePrescription(request));
    }
}
