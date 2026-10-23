package com.mediconnect.repository;

import com.mediconnect.model.HealthWorker;
import com.mediconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface HealthWorkerRepository extends JpaRepository<HealthWorker, Long> {
    Optional<HealthWorker> findByUser(User user);
    Optional<HealthWorker> findByUserId(Long userId);
    List<HealthWorker> findByVerificationStatus(String verificationStatus);
    List<HealthWorker> findByCityIgnoreCase(String city);
    List<HealthWorker> findByVerificationStatusAndIsAvailable(String verificationStatus, Boolean isAvailable);
}
