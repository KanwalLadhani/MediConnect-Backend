package com.mediconnect.dto;

public class AITriageRequest {
    private String symptoms;
    private String duration;
    private Integer patientAge;
    private String city;
    private String language = "en"; // "en" or "ur"

    public AITriageRequest() {}

    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public Integer getPatientAge() { return patientAge; }
    public void setPatientAge(Integer patientAge) { this.patientAge = patientAge; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
