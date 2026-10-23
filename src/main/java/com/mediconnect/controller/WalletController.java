package com.mediconnect.controller;

import com.mediconnect.dto.WalletTopupRequest;
import com.mediconnect.model.HealthWorker;
import com.mediconnect.model.WalletTransaction;
import com.mediconnect.repository.HealthWorkerRepository;
import com.mediconnect.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired private WalletTransactionRepository walletTransactionRepository;
    @Autowired private HealthWorkerRepository healthWorkerRepository;

    @GetMapping(value = {"/worker/{workerId}", "/{workerId}/transactions"})
    public ResponseEntity<List<WalletTransaction>> getTransactions(@PathVariable Long workerId) {
        return ResponseEntity.ok(walletTransactionRepository.findByWorkerIdOrderByCreatedAtDesc(workerId));
    }

    @GetMapping("/worker/{workerId}/balance")
    public ResponseEntity<?> getBalance(@PathVariable Long workerId) {
        return healthWorkerRepository.findById(workerId).map(worker ->
            ResponseEntity.ok(Map.of(
                "workerId", worker.getId(),
                "balance", worker.getWalletBalancePkr(),
                "workerName", worker.getUser().getFullName()
            ))
        ).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/topup")
    public ResponseEntity<?> topupWallet(@RequestBody WalletTopupRequest request) {
        HealthWorker worker = healthWorkerRepository.findById(request.getWorkerId()).orElse(null);
        if (worker == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Worker not found"));
        }
        String type = "TOPUP_" + (request.getMethod() != null ? request.getMethod().toUpperCase() : "JAZZCASH");
        WalletTransaction tx = new WalletTransaction(
            request.getWorkerId(),
            request.getAmountPkr(),
            type,
            request.getMethod() + " top-up",
            request.getTransactionReference(),
            "PENDING"
        );
        walletTransactionRepository.save(tx);
        worker.setWalletBalancePkr(worker.getWalletBalancePkr() + request.getAmountPkr());
        healthWorkerRepository.save(worker);
        return ResponseEntity.ok(Map.of("transaction", tx, "newBalance", worker.getWalletBalancePkr()));
    }
}
