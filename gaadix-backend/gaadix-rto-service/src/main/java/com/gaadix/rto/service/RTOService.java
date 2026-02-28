package com.gaadix.rto.service;

import com.gaadix.common.entity.RTOVerification;
import com.gaadix.common.enums.VerificationStatus;
import com.gaadix.common.exception.ResourceNotFoundException;
import com.gaadix.rto.dto.RTOVerificationRequest;
import com.gaadix.rto.dto.RTOVerificationResponse;
import com.gaadix.rto.entity.RTOVerificationEntity;
import com.gaadix.rto.repository.RTOVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RTOService {
    
    private final RTOVerificationRepository repository;
    private final Random random = new Random();
    
    @Transactional
    public RTOVerificationResponse verifyVehicle(RTOVerificationRequest request) {
        RTOVerificationEntity entity = repository.findByRegistrationNumber(request.getRegistrationNumber())
                .orElse(RTOVerificationEntity.builder()
                        .registrationNumber(request.getRegistrationNumber())
                        .rtoCode(request.getRtoCode())
                        .build());
        
        entity.setVerificationAttempts(entity.getVerificationAttempts() + 1);
        entity.setStatus(VerificationStatus.PENDING);
        
        boolean isValid = mockRTOVerification(request.getRegistrationNumber());
        
        if (isValid) {
            entity.setStatus(VerificationStatus.VERIFIED);
            entity.setState(extractState(request.getRtoCode()));
            entity.setOwnerName("Mock Owner " + random.nextInt(1000));
            entity.setVehicleClass("LMV");
            entity.setFuelType(request.getFuelType());
            entity.setManufacturer(request.getManufacturer());
            entity.setModel(request.getModel());
            entity.setManufacturingYear(request.getYear());
            entity.setRegistrationDate(LocalDate.now().minusYears(LocalDate.now().getYear() - request.getYear()));
            entity.setFitnessUpto(LocalDate.now().plusYears(15));
            entity.setInsuranceUpto(LocalDate.now().plusMonths(6));
            entity.setPucUpto(LocalDate.now().plusMonths(6));
            entity.setHasNOC(true);
            entity.setIsBlacklisted(false);
            entity.setVerificationRemarks("Vehicle verified successfully");
        } else {
            entity.setStatus(VerificationStatus.FAILED);
            entity.setVerificationRemarks("Invalid registration number or RTO code mismatch");
        }
        
        RTOVerificationEntity saved = repository.save(entity);
        return mapToResponse(saved);
    }
    
    @Transactional(readOnly = true)
    public RTOVerificationResponse getVerification(String registrationNumber) {
        RTOVerificationEntity entity = repository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Verification not found"));
        return mapToResponse(entity);
    }
    
    private boolean mockRTOVerification(String registrationNumber) {
        return registrationNumber != null && 
               registrationNumber.length() >= 8 && 
               registrationNumber.matches("^[A-Z]{2}[0-9]{1,2}[A-Z]{1,2}[0-9]{4}$");
    }
    
    private String extractState(String rtoCode) {
        if (rtoCode == null || rtoCode.length() < 2) return "Unknown";
        String stateCode = rtoCode.substring(0, 2);
        return switch (stateCode) {
            case "MH" -> "Maharashtra";
            case "DL" -> "Delhi";
            case "KA" -> "Karnataka";
            case "TN" -> "Tamil Nadu";
            case "GJ" -> "Gujarat";
            default -> "India";
        };
    }
    
    private RTOVerificationResponse mapToResponse(RTOVerificationEntity entity) {
        return RTOVerificationResponse.builder()
                .id(entity.getId())
                .registrationNumber(entity.getRegistrationNumber())
                .rtoCode(entity.getRtoCode())
                .state(entity.getState())
                .ownerName(entity.getOwnerName())
                .vehicleClass(entity.getVehicleClass())
                .fuelType(entity.getFuelType())
                .manufacturer(entity.getManufacturer())
                .model(entity.getModel())
                .manufacturingYear(entity.getManufacturingYear())
                .registrationDate(entity.getRegistrationDate())
                .fitnessUpto(entity.getFitnessUpto())
                .insuranceUpto(entity.getInsuranceUpto())
                .pucUpto(entity.getPucUpto())
                .status(entity.getStatus())
                .verificationRemarks(entity.getVerificationRemarks())
                .hasNOC(entity.getHasNOC())
                .isBlacklisted(entity.getIsBlacklisted())
                .verificationAttempts(entity.getVerificationAttempts())
                .build();
    }
}
