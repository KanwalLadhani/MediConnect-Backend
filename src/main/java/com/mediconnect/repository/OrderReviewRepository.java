package com.mediconnect.repository;

import com.mediconnect.model.OrderReview;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderReviewRepository extends JpaRepository<OrderReview, Long> {
    List<OrderReview> findByWorkerIdOrderByCreatedAtDesc(Long workerId);
    List<OrderReview> findByOrderId(Long orderId);
}
