package com.mediconnect.dto;

import com.mediconnect.model.HealthWorker;
import java.util.List;

public class AITriageResponse {
    private String urgencyLevel; // "EMERGENCY", "URGENT", "ROUTINE"
    private String urgencyBadgeUr;
    private Boolean isEmergency;
    private String suggestedServiceCategory;
    private Long suggestedServiceCategoryId;
    private String suggestedWorkerType;
    private String clinicalAssessmentEn;
    private String clinicalAssessmentUr;
    private List<String> firstAidInstructionsEn;
    private List<String> firstAidInstructionsUr;
    private Double confidenceScore;
    private List<String> detectedKeywords;
    private List<HealthWorker> availableWorkers;

    public AITriageResponse() {}

    public String getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(String urgencyLevel) { this.urgencyLevel = urgencyLevel; }

    public String getUrgencyBadgeUr() { return urgencyBadgeUr; }
    public void setUrgencyBadgeUr(String urgencyBadgeUr) { this.urgencyBadgeUr = urgencyBadgeUr; }

    public Boolean getIsEmergency() { return isEmergency; }
    public void setIsEmergency(Boolean isEmergency) { this.isEmergency = isEmergency; }

    public String getSuggestedServiceCategory() { return suggestedServiceCategory; }
    public void setSuggestedServiceCategory(String suggestedServiceCategory) { this.suggestedServiceCategory = suggestedServiceCategory; }

    public Long getSuggestedServiceCategoryId() { return suggestedServiceCategoryId; }
    public void setSuggestedServiceCategoryId(Long suggestedServiceCategoryId) { this.suggestedServiceCategoryId = suggestedServiceCategoryId; }

    public String getSuggestedWorkerType() { return suggestedWorkerType; }
    public void setSuggestedWorkerType(String suggestedWorkerType) { this.suggestedWorkerType = suggestedWorkerType; }

    public String getClinicalAssessmentEn() { return clinicalAssessmentEn; }
    public void setClinicalAssessmentEn(String clinicalAssessmentEn) { this.clinicalAssessmentEn = clinicalAssessmentEn; }

    public String getClinicalAssessmentUr() { return clinicalAssessmentUr; }
    public void setClinicalAssessmentUr(String clinicalAssessmentUr) { this.clinicalAssessmentUr = clinicalAssessmentUr; }

    public List<String> getFirstAidInstructionsEn() { return firstAidInstructionsEn; }
    public void setFirstAidInstructionsEn(List<String> firstAidInstructionsEn) { this.firstAidInstructionsEn = firstAidInstructionsEn; }

    public List<String> getFirstAidInstructionsUr() { return firstAidInstructionsUr; }
    public void setFirstAidInstructionsUr(List<String> firstAidInstructionsUr) { this.firstAidInstructionsUr = firstAidInstructionsUr; }

    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }

    public List<String> getDetectedKeywords() { return detectedKeywords; }
    public void setDetectedKeywords(List<String> detectedKeywords) { this.detectedKeywords = detectedKeywords; }

    public List<HealthWorker> getAvailableWorkers() { return availableWorkers; }
    public void setAvailableWorkers(List<HealthWorker> availableWorkers) { this.availableWorkers = availableWorkers; }
}
