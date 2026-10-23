package com.mediconnect.repository;

import com.mediconnect.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    List<ServiceOrder> findByPatientIdOrderByCreatedAtDesc(Long patientId);
    List<ServiceOrder> findByWorkerIdOrderByCreatedAtDesc(Long workerId);
    List<ServiceOrder> findByStatusOrderByCreatedAtDesc(String status);
    List<ServiceOrder> findAllByOrderByCreatedAtDesc();
}
