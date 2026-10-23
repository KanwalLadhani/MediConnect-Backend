package com.mediconnect.config;

import com.mediconnect.model.*;
import com.mediconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private HealthWorkerRepository healthWorkerRepository;
    @Autowired private ServiceCategoryRepository serviceCategoryRepository;
    @Autowired private ServiceOrderRepository serviceOrderRepository;
    @Autowired private OrderReviewRepository orderReviewRepository;
    @Autowired private ChatMessageRepository chatMessageRepository;
    @Autowired private WalletTransactionRepository walletTransactionRepository;

    @Override
    public void run(String... args) {
        // Only seed if no users exist
        if (userRepository.count() > 0) return;

        // ========== ADMIN ==========
        User admin = new User("admin@mediconnect.pk", "admin123", "MediConnect Admin", "03001234567", "ADMIN", "Karachi", null, "en", null);
        userRepository.save(admin);

        // ========== PATIENTS ==========
        User patient1 = new User("alizafar@mail.com", "patient123", "Ali Zafar", "03112345678", "PATIENT", "Karachi", "Block 8, Gulshan-e-Iqbal, Karachi", "en", null);
        userRepository.save(patient1);

        User patient2 = new User("fatima.bibi@mail.com", "patient123", "Fatima Bibi", "03209876543", "PATIENT", "Lahore", "House 12, Model Town, Lahore", "ur", null);
        userRepository.save(patient2);

        User patient3 = new User("usman.raza@mail.com", "patient123", "Usman Raza", "03451234567", "PATIENT", "Karachi", "Flat 5A, North Nazimabad, Karachi", "en", null);
        userRepository.save(patient3);

        // ========== WORKER USERS ==========
        User wu1 = new User("ayesha.nurse@mediconnect.pk", "worker123", "Ayesha Khan", "03211234567", "HEALTH_WORKER", "Karachi", "PECHS Block 6", "en", null);
        userRepository.save(wu1);

        User wu2 = new User("amjad.doc@mediconnect.pk", "worker123", "Dr. Amjad Ali Shah", "03001239876", "HEALTH_WORKER", "Karachi", "Clifton Block 4", "en", null);
        userRepository.save(wu2);

        User wu3 = new User("sana.lab@mediconnect.pk", "worker123", "Sana Naz", "03334567890", "HEALTH_WORKER", "Lahore", "Township, Lahore", "ur", null);
        userRepository.save(wu3);

        User wu4 = new User("bilal.disp@mediconnect.pk", "worker123", "Bilal Ahmed", "03161234567", "HEALTH_WORKER", "Karachi", "Korangi Sector 33", "ur", null);
        userRepository.save(wu4);

        // ========== HEALTH WORKERS ==========
        HealthWorker hw1 = new HealthWorker();
        hw1.setUser(wu1);
        hw1.setWorkerType("NURSE");
        hw1.setQualification("BS Nursing (PNC Registered)");
        hw1.setExperienceYears(6);
        hw1.setBio("Dedicated home-visit nurse specializing in drip setups, injections, and post-operative care. PNC-verified with 6 years of clinical experience.");
        hw1.setCity("Karachi");
        hw1.setServiceArea("Gulshan-e-Iqbal, North Nazimabad, PECHS, DHA");
        hw1.setVerificationStatus("APPROVED");
        hw1.setIsAvailable(true);
        hw1.setAverageRating(4.9);
        hw1.setTotalReviews(32);
        hw1.setTotalCompletedOrders(67);
        hw1.setBaseFeePkr(700.0);
        hw1.setWalletBalancePkr(4500.0);
        healthWorkerRepository.save(hw1);

        HealthWorker hw2 = new HealthWorker();
        hw2.setUser(wu2);
        hw2.setWorkerType("DOCTOR");
        hw2.setQualification("MBBS (PMDC Registered)");
        hw2.setExperienceYears(12);
        hw2.setBio("General physician offering home consultations, prescriptions, and emergency first-aid. PMDC-licensed with hospital emergency department experience.");
        hw2.setCity("Karachi");
        hw2.setServiceArea("Clifton, DHA, Saddar, Defence");
        hw2.setVerificationStatus("APPROVED");
        hw2.setIsAvailable(true);
        hw2.setAverageRating(4.8);
        hw2.setTotalReviews(18);
        hw2.setTotalCompletedOrders(45);
        hw2.setBaseFeePkr(1500.0);
        hw2.setWalletBalancePkr(6200.0);
        healthWorkerRepository.save(hw2);

        HealthWorker hw3 = new HealthWorker();
        hw3.setUser(wu3);
        hw3.setWorkerType("LAB_COLLECTOR");
        hw3.setQualification("Medical Lab Technology (Registered)");
        hw3.setExperienceYears(4);
        hw3.setBio("Home-visit phlebotomist. Blood samples, CBC, thyroid profiles, glucose tests delivered to partner labs for rapid results.");
        hw3.setCity("Lahore");
        hw3.setServiceArea("Model Town, Gulberg, Johar Town, Township");
        hw3.setVerificationStatus("APPROVED");
        hw3.setIsAvailable(true);
        hw3.setAverageRating(4.7);
        hw3.setTotalReviews(22);
        hw3.setTotalCompletedOrders(38);
        hw3.setBaseFeePkr(500.0);
        hw3.setWalletBalancePkr(2100.0);
        healthWorkerRepository.save(hw3);

        HealthWorker hw4 = new HealthWorker();
        hw4.setUser(wu4);
        hw4.setWorkerType("DISPENSER");
        hw4.setQualification("Diploma in Dispensing (RHWC Approved)");
        hw4.setExperienceYears(8);
        hw4.setBio("Experienced wound care specialist. Handles dressing, stitching aftercare, and post-surgical wound management. Government-certified dispenser.");
        hw4.setCity("Karachi");
        hw4.setServiceArea("Korangi, Landhi, Shah Faisal Colony, Malir");
        hw4.setVerificationStatus("APPROVED");
        hw4.setIsAvailable(true);
        hw4.setAverageRating(4.6);
        hw4.setTotalReviews(14);
        hw4.setTotalCompletedOrders(29);
        hw4.setBaseFeePkr(600.0);
        hw4.setWalletBalancePkr(1800.0);
        healthWorkerRepository.save(hw4);

        // ========== SERVICE CATEGORIES ==========
        serviceCategoryRepository.save(new ServiceCategory("Bandage & Wound Care", "پٹی اور زخم کی دیکھ بھال", "Professional wound cleaning, antiseptic application, and sterile dressing.", "زخم کی صفائی، اینٹی سیپٹک لگانا، اور جراثیم سے پاک ڈریسنگ۔", "Bandage", 600.0, 25));
        serviceCategoryRepository.save(new ServiceCategory("Injection", "انجیکشن", "Safe IM/IV/SC injection administration by a certified nurse at home.", "گھر پر تصدیق شدہ نرس کے ذریعے محفوظ انجیکشن لگوائیں۔", "Syringe", 500.0, 15));
        serviceCategoryRepository.save(new ServiceCategory("IV Drip Setup", "ڈرپ لگانا", "IV cannulation and drip infusion under supervised clinical care.", "نرس کی نگرانی میں ڈرپ لگانا اور نس کھولنا۔", "Droplets", 1200.0, 45));
        serviceCategoryRepository.save(new ServiceCategory("Blood Sample Collection", "خون کا نمونہ لینا", "At-home phlebotomy for lab tests. Results delivered within 24 hours.", "گھر سے خون کا نمونہ لیا جائے گا۔ 24 گھنٹے میں رپورٹ آ جائے گی۔", "TestTube", 800.0, 20));
        serviceCategoryRepository.save(new ServiceCategory("Stitches Support", "ٹانکوں کی دیکھ بھال", "Post-surgical stitch care, cleaning, and suture removal.", "ٹانکوں کی صفائی اور آپریشن کے بعد دیکھ بھال۔", "Scissors", 900.0, 30));
        serviceCategoryRepository.save(new ServiceCategory("Basic Vitals Checkup", "بنیادی معائنہ", "BP monitoring, temperature, heart rate, SpO2, and general health assessment.", "بلڈ پریشر، درجہ حرارت، دل کی دھڑکن، اور عمومی صحت کا جائزہ۔", "HeartPulse", 700.0, 20));
        serviceCategoryRepository.save(new ServiceCategory("Post-Op Wound Management", "آپریشن کے بعد زخم کا انتظام", "Advanced surgical wound care and catheter management at home.", "آپریشن کے بعد زخموں کی اعلی دیکھ بھال اور کیتھیٹر کا انتظام۔", "Activity", 1500.0, 40));

        // ========== SAMPLE ORDERS ==========
        ServiceCategory cat1 = serviceCategoryRepository.findAll().get(0);
        ServiceCategory cat2 = serviceCategoryRepository.findAll().get(1);
        ServiceCategory cat3 = serviceCategoryRepository.findAll().get(2);

        ServiceOrder order1 = new ServiceOrder();
        order1.setPatient(patient1);
        order1.setWorker(hw1);
        order1.setServiceCategory(cat1);
        order1.setSymptomsDescription("My hand got burned while cooking and needs bandage treatment.");
        order1.setUrgencyLevel("URGENT");
        order1.setStatus("COMPLETED");
        order1.setPatientAddress("Block 8, Gulshan-e-Iqbal, Karachi");
        order1.setPatientCity("Karachi");
        order1.setPatientPhone("03112345678");
        order1.setTotalAmountPkr(700.0);
        order1.setPlatformFeePkr(105.0);
        order1.setWorkerEarningPkr(595.0);
        order1.setEtaMinutes(0);
        order1.setCreatedAt(LocalDateTime.now().minusDays(3));
        order1.setCompletedAt(LocalDateTime.now().minusDays(3).plusMinutes(40));
        serviceOrderRepository.save(order1);

        ServiceOrder order2 = new ServiceOrder();
        order2.setPatient(patient3);
        order2.setWorker(hw1);
        order2.setServiceCategory(cat2);
        order2.setSymptomsDescription("Doctor prescribed antibiotic injections for 5 days. Need nurse visit.");
        order2.setUrgencyLevel("ROUTINE");
        order2.setStatus("IN_PROGRESS");
        order2.setPatientAddress("Flat 5A, North Nazimabad, Karachi");
        order2.setPatientCity("Karachi");
        order2.setPatientPhone("03451234567");
        order2.setTotalAmountPkr(500.0);
        order2.setPlatformFeePkr(75.0);
        order2.setWorkerEarningPkr(425.0);
        order2.setEtaMinutes(12);
        order2.setWorkerLatitude(24.9400);
        order2.setWorkerLongitude(67.0700);
        order2.setCreatedAt(LocalDateTime.now().minusHours(1));
        serviceOrderRepository.save(order2);

        ServiceOrder order3 = new ServiceOrder();
        order3.setPatient(patient2);
        order3.setWorker(hw3);
        order3.setServiceCategory(cat3);
        order3.setSymptomsDescription("Need blood test for thyroid profile. Doctor has recommended fasting sample.");
        order3.setUrgencyLevel("ROUTINE");
        order3.setStatus("ACCEPTED");
        order3.setPatientAddress("House 12, Model Town, Lahore");
        order3.setPatientCity("Lahore");
        order3.setPatientPhone("03209876543");
        order3.setTotalAmountPkr(800.0);
        order3.setPlatformFeePkr(120.0);
        order3.setWorkerEarningPkr(680.0);
        order3.setEtaMinutes(25);
        order3.setCreatedAt(LocalDateTime.now().minusMinutes(30));
        serviceOrderRepository.save(order3);

        ServiceOrder order4 = new ServiceOrder();
        order4.setPatient(patient1);
        order4.setWorker(null);
        order4.setServiceCategory(cat2);
        order4.setSymptomsDescription("Severe body aches and high fever. Need injection for pain relief.");
        order4.setUrgencyLevel("URGENT");
        order4.setStatus("REQUESTED");
        order4.setPatientAddress("Block 8, Gulshan-e-Iqbal, Karachi");
        order4.setPatientCity("Karachi");
        order4.setPatientPhone("03112345678");
        order4.setTotalAmountPkr(500.0);
        order4.setEtaMinutes(0);
        order4.setCreatedAt(LocalDateTime.now());
        serviceOrderRepository.save(order4);

        // ========== REVIEWS ==========
        orderReviewRepository.save(new OrderReview(order1.getId(), hw1.getId(), patient1.getId(), "Ali Zafar", 5, "Ayesha arrived quickly and was very professional. She cleaned the wound carefully with proper sterilization. Highly recommend!"));
        orderReviewRepository.save(new OrderReview(null, hw1.getId(), patient3.getId(), "Usman Raza", 5, "Best nurse in Karachi. Always on time and explains everything clearly. My grandmother feels comfortable with her."));
        orderReviewRepository.save(new OrderReview(null, hw2.getId(), patient1.getId(), "Ali Zafar", 4, "Dr. Amjad was thorough in his checkup. Gave clear prescriptions. Slightly late but very competent."));
        orderReviewRepository.save(new OrderReview(null, hw3.getId(), patient2.getId(), "Fatima Bibi", 5, "Sana collected blood samples expertly. No bruising at all. Reports came within 24 hours. Very satisfied."));
        orderReviewRepository.save(new OrderReview(null, hw4.getId(), patient1.getId(), "Ali Zafar", 4, "Bilal did a professional dressing. Used clean equipment. Very affordable rates."));

        // ========== CHAT MESSAGES ==========
        chatMessageRepository.save(new ChatMessage(order2.getId(), "PATIENT", "Usman Raza", "Assalam u Alaikum! When will you be arriving?"));
        chatMessageRepository.save(new ChatMessage(order2.getId(), "WORKER", "Ayesha Khan", "Walaikum Assalam! I'm on my way. Will reach in about 12 minutes InshaAllah."));
        chatMessageRepository.save(new ChatMessage(order2.getId(), "PATIENT", "Usman Raza", "JazakAllah. My grandmother is waiting. She needs the antibiotic injection."));
        chatMessageRepository.save(new ChatMessage(order2.getId(), "WORKER", "Ayesha Khan", "InshaAllah no worries. I have all the supplies. Has she eaten something today?"));
        chatMessageRepository.save(new ChatMessage(order2.getId(), "PATIENT", "Usman Raza", "Yes she had porridge this morning. The injection is Rocephin 1g IM."));
        chatMessageRepository.save(new ChatMessage(order2.getId(), "SYSTEM", "MediConnect", "Worker Ayesha Khan is on the way. Estimated arrival: 12 minutes."));

        // ========== WALLET TRANSACTIONS ==========
        walletTransactionRepository.save(new WalletTransaction(hw1.getId(), 595.0, "CREDIT_EARNING", "Earnings from order #" + order1.getId(), null, "COMPLETED"));
        walletTransactionRepository.save(new WalletTransaction(hw1.getId(), 2000.0, "TOPUP_JAZZCASH", "JazzCash top-up", "JC-20261023-001", "COMPLETED"));
        walletTransactionRepository.save(new WalletTransaction(hw2.getId(), 3000.0, "TOPUP_EASYPAISA", "EasyPaisa top-up", "EP-20261023-002", "COMPLETED"));
        walletTransactionRepository.save(new WalletTransaction(hw3.getId(), 680.0, "CREDIT_EARNING", "Earnings from blood test order", null, "COMPLETED"));
        walletTransactionRepository.save(new WalletTransaction(hw4.getId(), 1000.0, "TOPUP_JAZZCASH", "JazzCash top-up", "JC-20261023-003", "PENDING"));
    }
}
