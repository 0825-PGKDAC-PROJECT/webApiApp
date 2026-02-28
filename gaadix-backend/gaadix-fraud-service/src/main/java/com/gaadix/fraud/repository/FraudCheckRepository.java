package com.gaadix.fraud.repository;

import com.gaadix.fraud.entity.FraudCheck;
import com.gaadix.fraud.entity.FraudCheck.RiskLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FraudCheckRepository extends JpaRepository<FraudCheck, Long> {
    
    Optional<FraudCheck> findByTransactionId(Long transactionId);
    
    List<FraudCheck> findByUserId(Long userId);
    
    long countByUserId(Long userId);
    
    List<FraudCheck> findByIsFraudulentTrue();
    
    List<FraudCheck> findByRiskLevel(RiskLevel riskLevel);
    
    @Query("SELECT COUNT(f) FROM FraudCheck f WHERE f.userId = :userId AND f.createdAt > CURRENT_TIMESTAMP - 1")
    long countRecentTransactionsByUser(Long userId);
}
