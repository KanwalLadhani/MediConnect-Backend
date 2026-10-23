package com.mediconnect.dto;

public class OrderStatusUpdateRequest {
    private String status; // "ACCEPTED", "ON_THE_WAY", "IN_PROGRESS", "COMPLETED", "CANCELLED"
    private Integer etaMinutes;
    private Double workerLatitude;
    private Double workerLongitude;

    public OrderStatusUpdateRequest() {}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getEtaMinutes() { return etaMinutes; }
    public void setEtaMinutes(Integer etaMinutes) { this.etaMinutes = etaMinutes; }

    public Double getWorkerLatitude() { return workerLatitude; }
    public void setWorkerLatitude(Double workerLatitude) { this.workerLatitude = workerLatitude; }

    public Double getWorkerLongitude() { return workerLongitude; }
    public void setWorkerLongitude(Double workerLongitude) { this.workerLongitude = workerLongitude; }
}
