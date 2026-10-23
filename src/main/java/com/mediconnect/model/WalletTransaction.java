package com.mediconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transactions")
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long workerId;

    @Column(nullable = false)
    private Double amountPkr;

    @Column(nullable = false)
    private String type; // "CREDIT_EARNING", "DEBIT_COMMISSION", "TOPUP_JAZZCASH", "TOPUP_EASYPAISA"

    private String description;

    private String referenceNumber;

    private String status = "COMPLETED"; // "COMPLETED", "PENDING"

    private LocalDateTime createdAt = LocalDateTime.now();

    public WalletTransaction() {}

    public WalletTransaction(Long workerId, Double amountPkr, String type, String description, String referenceNumber, String status) {
        this.workerId = workerId;
        this.amountPkr = amountPkr;
        this.type = type;
        this.description = description;
        this.referenceNumber = referenceNumber;
        this.status = status != null ? status : "COMPLETED";
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getWorkerId() { return workerId; }
    public void setWorkerId(Long workerId) { this.workerId = workerId; }

    public Double getAmountPkr() { return amountPkr; }
    public void setAmountPkr(Double amountPkr) { this.amountPkr = amountPkr; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
