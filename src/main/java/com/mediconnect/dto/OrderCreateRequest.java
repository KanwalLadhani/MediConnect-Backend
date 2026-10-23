package com.mediconnect.dto;

public class OrderCreateRequest {
    private Long patientId;
    private Long workerId; // optional, can be null if auto-matching
    private Long serviceCategoryId;
    private String symptomsDescription;
    private String prescriptionNotes;
    private String urgencyLevel; // "ROUTINE", "URGENT", "EMERGENCY"
    private String patientAddress;
    private String patientCity;
    private String patientPhone;
    private Double customAmountPkr;

    public OrderCreateRequest() {}

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getWorkerId() { return workerId; }
    public void setWorkerId(Long workerId) { this.workerId = workerId; }

    public Long getServiceCategoryId() { return serviceCategoryId; }
    public void setServiceCategoryId(Long serviceCategoryId) { this.serviceCategoryId = serviceCategoryId; }

    public String getSymptomsDescription() { return symptomsDescription; }
    public void setSymptomsDescription(String symptomsDescription) { this.symptomsDescription = symptomsDescription; }

    public String getPrescriptionNotes() { return prescriptionNotes; }
    public void setPrescriptionNotes(String prescriptionNotes) { this.prescriptionNotes = prescriptionNotes; }

    public String getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(String urgencyLevel) { this.urgencyLevel = urgencyLevel; }

    public String getPatientAddress() { return patientAddress; }
    public void setPatientAddress(String patientAddress) { this.patientAddress = patientAddress; }

    public String getPatientCity() { return patientCity; }
    public void setPatientCity(String patientCity) { this.patientCity = patientCity; }

    public String getPatientPhone() { return patientPhone; }
    public void setPatientPhone(String patientPhone) { this.patientPhone = patientPhone; }

    public Double getCustomAmountPkr() { return customAmountPkr; }
    public void setCustomAmountPkr(Double customAmountPkr) { this.customAmountPkr = customAmountPkr; }
}
