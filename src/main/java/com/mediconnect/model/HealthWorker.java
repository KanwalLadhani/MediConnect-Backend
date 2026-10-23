package com.mediconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_workers")
public class HealthWorker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String workerType; // "DOCTOR", "NURSE", "MALE_NURSE", "OT_TECHNICIAN", "DISPENSER", "LAB_COLLECTOR"

    private String qualification; // e.g. "BS Nursing (PNC Registered)"

    private Integer experienceYears;

    @Column(length = 1000)
    private String bio;

    private String city;

    private String serviceArea;

    private String verificationStatus = "APPROVED"; // "APPROVED", "PENDING", "REJECTED"

    private Boolean isAvailable = true;

    private Double averageRating = 4.8;

    private Integer totalReviews = 12;

    private Integer totalCompletedOrders = 24;

    private Double baseFeePkr = 800.0;

    private Double walletBalancePkr = 3200.0;

    private LocalDateTime createdAt = LocalDateTime.now();

    public HealthWorker() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getWorkerType() { return workerType; }
    public void setWorkerType(String workerType) { this.workerType = workerType; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getServiceArea() { return serviceArea; }
    public void setServiceArea(String serviceArea) { this.serviceArea = serviceArea; }

    public String getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(String verificationStatus) { this.verificationStatus = verificationStatus; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public Integer getTotalReviews() { return totalReviews; }
    public void setTotalReviews(Integer totalReviews) { this.totalReviews = totalReviews; }

    public Integer getTotalCompletedOrders() { return totalCompletedOrders; }
    public void setTotalCompletedOrders(Integer totalCompletedOrders) { this.totalCompletedOrders = totalCompletedOrders; }

    public Double getBaseFeePkr() { return baseFeePkr; }
    public void setBaseFeePkr(Double baseFeePkr) { this.baseFeePkr = baseFeePkr; }

    public Double getWalletBalancePkr() { return walletBalancePkr; }
    public void setWalletBalancePkr(Double walletBalancePkr) { this.walletBalancePkr = walletBalancePkr; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
