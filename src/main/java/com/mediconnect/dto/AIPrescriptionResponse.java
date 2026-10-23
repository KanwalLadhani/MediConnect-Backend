package com.mediconnect.dto;

import com.mediconnect.model.HealthWorker;
import java.util.List;

public class AIPrescriptionResponse {
    private List<String> extractedMedicines;
    private List<String> identifiedProcedures;
    private String suggestedServiceCategory;
    private Long suggestedServiceCategoryId;
    private String recommendedWorkerType;
    private String instructionsEn;
    private String instructionsUr;
    private List<String> specialPrecautions;
    private List<HealthWorker> availableWorkers;

    public AIPrescriptionResponse() {}

    public List<String> getExtractedMedicines() { return extractedMedicines; }
    public void setExtractedMedicines(List<String> extractedMedicines) { this.extractedMedicines = extractedMedicines; }

    public List<String> getIdentifiedProcedures() { return identifiedProcedures; }
    public void setIdentifiedProcedures(List<String> identifiedProcedures) { this.identifiedProcedures = identifiedProcedures; }

    public String getSuggestedServiceCategory() { return suggestedServiceCategory; }
    public void setSuggestedServiceCategory(String suggestedServiceCategory) { this.suggestedServiceCategory = suggestedServiceCategory; }

    public Long getSuggestedServiceCategoryId() { return suggestedServiceCategoryId; }
    public void setSuggestedServiceCategoryId(Long suggestedServiceCategoryId) { this.suggestedServiceCategoryId = suggestedServiceCategoryId; }

    public String getRecommendedWorkerType() { return recommendedWorkerType; }
    public void setRecommendedWorkerType(String recommendedWorkerType) { this.recommendedWorkerType = recommendedWorkerType; }

    public String getInstructionsEn() { return instructionsEn; }
    public void setInstructionsEn(String instructionsEn) { this.instructionsEn = instructionsEn; }

    public String getInstructionsUr() { return instructionsUr; }
    public void setInstructionsUr(String instructionsUr) { this.instructionsUr = instructionsUr; }

    public List<String> getSpecialPrecautions() { return specialPrecautions; }
    public void setSpecialPrecautions(List<String> specialPrecautions) { this.specialPrecautions = specialPrecautions; }

    public List<HealthWorker> getAvailableWorkers() { return availableWorkers; }
    public void setAvailableWorkers(List<HealthWorker> availableWorkers) { this.availableWorkers = availableWorkers; }
}
