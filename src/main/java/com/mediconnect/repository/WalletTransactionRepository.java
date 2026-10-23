package com.mediconnect.repository;

import com.mediconnect.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    List<WalletTransaction> findByWorkerIdOrderByCreatedAtDesc(Long workerId);
}
