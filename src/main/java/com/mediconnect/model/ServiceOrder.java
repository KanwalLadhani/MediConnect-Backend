package com.mediconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_orders")
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id")
    private HealthWorker worker;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_category_id", nullable = false)
    private ServiceCategory serviceCategory;

    @Column(length = 2000)
    private String symptomsDescription;

    @Column(length = 2000)
    private String prescriptionNotes;

    private String urgencyLevel = "ROUTINE"; // "ROUTINE", "URGENT", "EMERGENCY"

    private String status = "REQUESTED"; // "REQUESTED", "ACCEPTED", "ON_THE_WAY", "IN_PROGRESS", "COMPLETED", "CANCELLED"

    private String patientAddress;

    private String patientCity;

    private String patientPhone;

    private Double totalAmountPkr;

    private Double platformFeePkr;

    private Double workerEarningPkr;

    private Integer etaMinutes = 20;

    private Double workerLatitude;

    private Double workerLongitude;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime completedAt;

    public ServiceOrder() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getPatient() { return patient; }
    public void setPatient(User patient) { this.patient = patient; }

    public HealthWorker getWorker() { return worker; }
    public void setWorker(HealthWorker worker) { this.worker = worker; }

    public ServiceCategory getServiceCategory() { return serviceCategory; }
    public void setServiceCategory(ServiceCategory serviceCategory) { this.serviceCategory = serviceCategory; }

    public String getSymptomsDescription() { return symptomsDescription; }
    public void setSymptomsDescription(String symptomsDescription) { this.symptomsDescription = symptomsDescription; }

    public String getPrescriptionNotes() { return prescriptionNotes; }
    public void setPrescriptionNotes(String prescriptionNotes) { this.prescriptionNotes = prescriptionNotes; }

    public String getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(String urgencyLevel) { this.urgencyLevel = urgencyLevel; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPatientAddress() { return patientAddress; }
    public void setPatientAddress(String patientAddress) { this.patientAddress = patientAddress; }

    public String getPatientCity() { return patientCity; }
    public void setPatientCity(String patientCity) { this.patientCity = patientCity; }

    public String getPatientPhone() { return patientPhone; }
    public void setPatientPhone(String patientPhone) { this.patientPhone = patientPhone; }

    public Double getTotalAmountPkr() { return totalAmountPkr; }
    public void setTotalAmountPkr(Double totalAmountPkr) { this.totalAmountPkr = totalAmountPkr; }

    public Double getPlatformFeePkr() { return platformFeePkr; }
    public void setPlatformFeePkr(Double platformFeePkr) { this.platformFeePkr = platformFeePkr; }

    public Double getWorkerEarningPkr() { return workerEarningPkr; }
    public void setWorkerEarningPkr(Double workerEarningPkr) { this.workerEarningPkr = workerEarningPkr; }

    public Integer getEtaMinutes() { return etaMinutes; }
    public void setEtaMinutes(Integer etaMinutes) { this.etaMinutes = etaMinutes; }

    public Double getWorkerLatitude() { return workerLatitude; }
    public void setWorkerLatitude(Double workerLatitude) { this.workerLatitude = workerLatitude; }

    public Double getWorkerLongitude() { return workerLongitude; }
    public void setWorkerLongitude(Double workerLongitude) { this.workerLongitude = workerLongitude; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
