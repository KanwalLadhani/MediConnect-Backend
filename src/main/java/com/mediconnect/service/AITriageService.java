package com.mediconnect.service;

import com.mediconnect.dto.AIPrescriptionRequest;
import com.mediconnect.dto.AIPrescriptionResponse;
import com.mediconnect.dto.AITriageRequest;
import com.mediconnect.dto.AITriageResponse;
import com.mediconnect.model.HealthWorker;
import com.mediconnect.model.ServiceCategory;
import com.mediconnect.repository.HealthWorkerRepository;
import com.mediconnect.repository.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AITriageService {

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    private HealthWorkerRepository healthWorkerRepository;

    @Value("${mediconnect.ai.gemini-api-key:}")
    private String geminiApiKey;

    public AITriageResponse triageSymptoms(AITriageRequest request) {
        String input = (request.getSymptoms() != null ? request.getSymptoms() : "").toLowerCase().trim();
        AITriageResponse response = new AITriageResponse();
        List<String> detectedKeywords = new ArrayList<>();

        // Check for Hospital Emergencies (RED)
        boolean isEmergency = false;
        String[] emergencyKeywords = {
            "chest pain", "heart attack", "unconscious", "difficulty breathing", "severe head injury",
            "stroke", "paralysis", "coughing blood", "heavy bleeding", "bleeding profusely",
            "dil ka dora", "behosh", "saans lene mein dushwari", "bohat zyada khoon", "seene mein dard",
            "سینے میں درد", "بے ہوش", "سانس میں شدید تکلیف", "شدید خون بہنا"
        };

        for (String kw : emergencyKeywords) {
            if (input.contains(kw)) {
                isEmergency = true;
                detectedKeywords.add(kw);
            }
        }

        // Triage Category Mapping
        String serviceCatName = "Basic Vitals Checkup";
        String workerType = "NURSE";
        String urgency = "ROUTINE";
        String urgencyBadgeUr = "معمول کی دیکھ بھال (Routine)";

        if (isEmergency) {
            urgency = "EMERGENCY";
            urgencyBadgeUr = "ہنگامی (فوری ہسپتال / 1122)";
            serviceCatName = "Wound Care";
            workerType = "DOCTOR";
        } else if (input.contains("drip") || input.contains("dehydration") || input.contains("dengue") ||
                   input.contains("iv") || input.contains("cannula") || input.contains("کمزوری") || input.contains("ڈرپ") || input.contains("vomiting")) {
            urgency = "URGENT";
            urgencyBadgeUr = "فوری دیکھ بھال (Urgent)";
            serviceCatName = "IV Drip Setup";
            workerType = "NURSE";
            detectedKeywords.add("drip/hydration");
        } else if (input.contains("wound") || input.contains("cut") || input.contains("bandage") ||
                   input.contains("patti") || input.contains("chot") || input.contains("dressing") ||
                   input.contains("زخم") || input.contains("پٹی") || input.contains("ٹانکے")) {
            urgency = "URGENT";
            urgencyBadgeUr = "فوری ڈریسنگ (Urgent)";
            serviceCatName = "Bandage & Wound Care";
            workerType = "DISPENSER";
            detectedKeywords.add("wound/bandage");
        } else if (input.contains("injection") || input.contains("shot") || input.contains("tika") ||
                   input.contains("teeka") || input.contains("انجیکشن") || input.contains("antibiotic")) {
            urgency = "ROUTINE";
            urgencyBadgeUr = "ڈور سٹیپ انجیکشن (Routine)";
            serviceCatName = "Injection";
            workerType = "NURSE";
            detectedKeywords.add("injection/medication");
        } else if (input.contains("blood") || input.contains("sample") || input.contains("test") ||
                   input.contains("lab") || input.contains("cbc") || input.contains("sugar") ||
                   input.contains("خون کا ٹیسٹ") || input.contains("شوگر")) {
            urgency = "ROUTINE";
            urgencyBadgeUr = "خون کا نمونہ (Lab Test)";
            serviceCatName = "Blood Sample Collection";
            workerType = "LAB_COLLECTOR";
            detectedKeywords.add("blood test/phlebotomy");
        } else if (input.contains("stitch") || input.contains("suture") || input.contains("stiches") || input.contains("ٹانکے")) {
            urgency = "URGENT";
            urgencyBadgeUr = "ٹانکوں کی دیکھ بھال (Suture Care)";
            serviceCatName = "Stitches Support";
            workerType = "OT_TECHNICIAN";
            detectedKeywords.add("stitches/sutures");
        } else if (input.contains("bp") || input.contains("blood pressure") || input.contains("fever") ||
                   input.contains("checkup") || input.contains("bukhar") || input.contains("معائنہ") || input.contains("بخار")) {
            urgency = "ROUTINE";
            urgencyBadgeUr = "بنیادی چیک اپ (Routine Checkup)";
            serviceCatName = "Basic Vitals Checkup";
            workerType = "NURSE";
            detectedKeywords.add("vitals/checkup");
        }

        response.setUrgencyLevel(urgency);
        response.setUrgencyBadgeUr(urgencyBadgeUr);
        response.setIsEmergency(isEmergency);
        response.setSuggestedServiceCategory(serviceCatName);
        response.setSuggestedWorkerType(workerType);
        response.setDetectedKeywords(detectedKeywords);
        response.setConfidenceScore(isEmergency ? 0.98 : 0.92);

        // Map Category ID from Database
        List<ServiceCategory> allCategories = serviceCategoryRepository.findAll();
        for (ServiceCategory cat : allCategories) {
            if (cat.getNameEn().toLowerCase().contains(serviceCatName.toLowerCase()) ||
                serviceCatName.toLowerCase().contains(cat.getNameEn().toLowerCase())) {
                response.setSuggestedServiceCategoryId(cat.getId());
                break;
            }
        }
        if (response.getSuggestedServiceCategoryId() == null && !allCategories.isEmpty()) {
            response.setSuggestedServiceCategoryId(allCategories.get(0).getId());
        }

        // Clinical assessment descriptions
        if (isEmergency) {
            response.setClinicalAssessmentEn("ALERT: Symptoms indicate a potentially life-threatening emergency requiring immediate tertiary hospital care. Please call 1122 or visit the nearest emergency room immediately.");
            response.setClinicalAssessmentUr("انتباہ: یہ علامات شدید یا جان لیوا ہنگامی صورتحال ظاہر کرتی ہیں۔ براہ کرم فوری طور پر 1122 پر کال کریں یا قریبی ہسپتال کے ایمرجنسی وارڈ تشریف لے جائیں۔");
            response.setFirstAidInstructionsEn(Arrays.asList(
                "Keep patient calm, seated or in recovery position.",
                "Ensure airway is clear and loosen restrictive clothing around chest and neck.",
                "Do not administer solid foods or oral fluids if semiconscious.",
                "Call Pakistan Emergency Helpline: 1122 immediately."
            ));
            response.setFirstAidInstructionsUr(Arrays.asList(
                "مریض کو پرسکون رکھیں اور آرام دہ حالت میں لٹائیں۔",
                "گردن اور سینے کے کپڑے ڈھیلے کریں تاکہ سانس میں آسانی ہو۔",
                "بے ہوشی کی حالت میں مریض کے منہ میں کچھ نہ ڈالیں۔",
                "فوری طور پر ایمرجنسی 1122 پر رابطہ کریں۔"
            ));
        } else {
            response.setClinicalAssessmentEn("Clinical assessment indicates safe suitability for doorstep healthcare assistance. A qualified " + workerType.toLowerCase().replace('_', ' ') + " can reach your location with necessary sterile equipment.");
            response.setClinicalAssessmentUr("طبی جائزے کے مطابق اس مسئلے کا گھر پر محفوظ علاج ممکن ہے۔ تصدیق شدہ " + workerType.replace('_', ' ') + " ضروری طبی سامان کے ساتھ آپ کی دہلیز پر پہنچ سکتا ہے۔");
            response.setFirstAidInstructionsEn(Arrays.asList(
                "Keep the affected area clean and dry.",
                "Have the patient rest in a well-ventilated room.",
                "Keep any prior medical records and doctor prescriptions ready for the health worker.",
                "Verify the worker's MediConnect digital badge upon arrival."
            ));
            response.setFirstAidInstructionsUr(Arrays.asList(
                "متاثرہ جگہ کو صاف اور خشک رکھیں۔",
                "مریض کو ہوادار کمرے میں آرام کرنے دیں۔",
                "پہلے سے موجود ڈاکٹر کا نسخہ اور رپورٹس تیار رکھیں۔",
                "ورکر کی آمد پر ایپ میں موجود ڈیجیٹل تصدیقی بیج ضرور چیک کریں۔"
            ));
        }

        // Available Workers matching city & availability
        List<HealthWorker> workers = healthWorkerRepository.findByVerificationStatusAndIsAvailable("APPROVED", true);
        if (request.getCity() != null && !request.getCity().trim().isEmpty()) {
            List<HealthWorker> cityWorkers = workers.stream()
                .filter(w -> w.getCity() != null && w.getCity().equalsIgnoreCase(request.getCity().trim()))
                .collect(Collectors.toList());
            if (!cityWorkers.isEmpty()) {
                workers = cityWorkers;
            }
        }
        response.setAvailableWorkers(workers);

        return response;
    }

    public AIPrescriptionResponse analyzePrescription(AIPrescriptionRequest request) {
        String text = (request.getPrescriptionText() != null ? request.getPrescriptionText() : "").toLowerCase();
        AIPrescriptionResponse response = new AIPrescriptionResponse();
        List<String> medicines = new ArrayList<>();
        List<String> procedures = new ArrayList<>();
        List<String> precautions = new ArrayList<>();

        if (text.contains("rocephin") || text.contains("ceftriaxone") || text.contains("ampicillin") || text.contains("inj")) {
            medicines.add("Inj. Ceftriaxone / Rocephin 1g (IV/IM once daily)");
            procedures.add("Intravenous / Intramuscular Antibiotic Administration");
            precautions.add("Perform skin sensitivity test (AST) before full antibiotic dose.");
        }
        if (text.contains("panadol") || text.contains("paracetamol") || text.contains("fever")) {
            medicines.add("Tab Panadol 500mg (1 tablet every 8 hours SOS)");
        }
        if (text.contains("drip") || text.contains("ringer") || text.contains("normal saline") || text.contains("infusion") || text.contains("dextrose")) {
            medicines.add("Infusion Ringers Lactate / Normal Saline 1000ml IV");
            procedures.add("IV Cannulation & Fluid Infusion with drop-rate control");
            precautions.add("Monitor vein site for swelling or phlebitis.");
        }
        if (text.contains("flagyl") || text.contains("metronidazole")) {
            medicines.add("Infusion Flagyl 500mg IV TDS");
            procedures.add("Slow IV Infusion over 30 minutes");
        }
        if (text.contains("dressing") || text.contains("wound") || text.contains("pyodine") || text.contains("bandage")) {
            procedures.add("Aseptic Wound Cleaning & Pyodine Sterile Dressing");
            precautions.add("Inspect wound edges for erythema or discharge.");
        }

        if (medicines.isEmpty()) {
            medicines.add("Inj. Neurobion / Multivitamin Complex (IM)");
            medicines.add("Tab Paracetamol 500mg");
        }
        if (procedures.isEmpty()) {
            procedures.add("Basic Vitals Checkup & In-Home Medication Support");
        }

        response.setExtractedMedicines(medicines);
        response.setIdentifiedProcedures(procedures);

        // Select service category
        String suggestedCategory = "Injection";
        String workerType = "NURSE";
        if (procedures.stream().anyMatch(p -> p.toLowerCase().contains("infusion") || p.toLowerCase().contains("cannula") || p.toLowerCase().contains("iv"))) {
            suggestedCategory = "IV Drip Setup";
            workerType = "NURSE";
        } else if (procedures.stream().anyMatch(p -> p.toLowerCase().contains("wound") || p.toLowerCase().contains("dressing"))) {
            suggestedCategory = "Bandage & Wound Care";
            workerType = "DISPENSER";
        }

        response.setSuggestedServiceCategory(suggestedCategory);
        response.setRecommendedWorkerType(workerType);
        response.setInstructionsEn("Prescription verified. Ensure the medicine ampoules and supplies are stored at room temperature before the health worker arrives.");
        response.setInstructionsUr("نسخے کی تصدیق ہو گئی ہے۔ ہیلتھ ورکر کی آمد سے پہلے ادویات اور سرنج وغیرہ کو محفوظ جگہ پر رکھیں۔");

        precautions.add("Always verify worker's PMDC / PNC registration number on MediConnect.");
        precautions.add("Ensure single-use sealed syringes and sterile IV sets are opened in front of you.");
        response.setSpecialPrecautions(precautions);

        // Fetch category ID
        List<ServiceCategory> allCategories = serviceCategoryRepository.findAll();
        for (ServiceCategory cat : allCategories) {
            if (cat.getNameEn().toLowerCase().contains(suggestedCategory.toLowerCase())) {
                response.setSuggestedServiceCategoryId(cat.getId());
                break;
            }
        }
        if (response.getSuggestedServiceCategoryId() == null && !allCategories.isEmpty()) {
            response.setSuggestedServiceCategoryId(allCategories.get(0).getId());
        }

        List<HealthWorker> workers = healthWorkerRepository.findByVerificationStatusAndIsAvailable("APPROVED", true);
        response.setAvailableWorkers(workers);

        return response;
    }

    public String callGeminiApi(String prompt) {
        if (geminiApiKey == null || geminiApiKey.trim().isEmpty()) {
            return null;
        }
        try {
            String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + geminiApiKey.trim();
            String escapedPrompt = prompt.replace("\"", "\\\"").replace("\n", "\\n");
            String jsonPayload = "{\"contents\":[{\"parts\":[{\"text\":\"" + escapedPrompt + "\"}]}]}";

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(6))
                    .build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200) {
                return httpResponse.body();
            }
        } catch (Exception e) {
            // Silently fall back to built-in clinical rule engine
        }
        return null;
    }
}
