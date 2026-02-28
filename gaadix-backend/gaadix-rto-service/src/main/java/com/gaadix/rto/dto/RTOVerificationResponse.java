package com.gaadix.rto.dto;

import com.gaadix.common.enums.VerificationStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RTOVerificationResponse {
    
    private Long id;
    private String registrationNumber;
    private String rtoCode;
    private String state;
    private String ownerName;
    private String vehicleClass;
    private String fuelType;
    private String manufacturer;
    private String model;
    private Integer manufacturingYear;
    private LocalDate registrationDate;
    private LocalDate fitnessUpto;
    private LocalDate insuranceUpto;
    private LocalDate pucUpto;
    private VerificationStatus status;
    private String verificationRemarks;
    private Boolean hasNOC;
    private Boolean isBlacklisted;
    private Integer verificationAttempts;
}
