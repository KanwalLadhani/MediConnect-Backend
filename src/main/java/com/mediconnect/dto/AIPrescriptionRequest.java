package com.mediconnect.dto;

public class AIPrescriptionRequest {
    private String prescriptionText;
    private String imageBase64;
    private String city;

    public AIPrescriptionRequest() {}

    public String getPrescriptionText() { return prescriptionText; }
    public void setPrescriptionText(String prescriptionText) { this.prescriptionText = prescriptionText; }

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}
