package com.gaadix.rto.repository;

import com.gaadix.common.enums.VerificationStatus;
import com.gaadix.rto.entity.RTOVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RTOVerificationRepository extends JpaRepository<RTOVerificationEntity, Long> {
    
    Optional<RTOVerificationEntity> findByRegistrationNumber(String registrationNumber);
    
    List<RTOVerificationEntity> findByStatus(VerificationStatus status);
    
    List<RTOVerificationEntity> findByRtoCode(String rtoCode);
    
    List<RTOVerificationEntity> findByIsBlacklistedTrue();
}
