package com.gaadix.rto.entity;

import com.gaadix.common.entity.BaseEntity;
import com.gaadix.common.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "rto_verifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RTOVerificationEntity extends BaseEntity {
    
    @Column(nullable = false, unique = true)
    private String registrationNumber;
    
    @Column(nullable = false)
    private String rtoCode;
    
    private String state;
    private String ownerName;
    private String vehicleClass;
    private String fuelType;
    private String manufacturer;
    private String model;
    private Integer manufacturingYear;
    private String engineNumber;
    private String chassisNumber;
    
    private LocalDate registrationDate;
    private LocalDate fitnessUpto;
    private LocalDate insuranceUpto;
    private LocalDate pucUpto;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private VerificationStatus status = VerificationStatus.PENDING;
    
    private String verificationRemarks;
    private Boolean hasNOC;
    private Boolean isBlacklisted;
    
    @Builder.Default
    private Integer verificationAttempts = 0;
}
