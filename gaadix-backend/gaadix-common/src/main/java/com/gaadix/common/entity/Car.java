package com.gaadix.common.entity;

import com.gaadix.common.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car extends BaseEntity {
    
    @NotBlank
    private String brand;
    
    @NotBlank
    private String model;
    
    @NotNull
    private Integer year;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private FuelType fuelType;
    
    private Integer batteryCapacity;
    private Integer electricRange;
    private Integer fuelTankCapacity;
    private Double mileageCity;
    private Double mileageHighway;
    
    @NotNull
    private BigDecimal basePrice;
    
    @NotNull
    private Integer odometer;
    
    private String color;
    
    @Column(unique = true)
    private String registrationNumber;
    
    private String registrationState;
    private String rtoCode;
    
    @Enumerated(EnumType.STRING)
    private OwnershipType ownershipType;
    
    @Enumerated(EnumType.STRING)
    private Transmission transmission;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CarStatus status = CarStatus.DRAFT;
    
    @Column(length = 2000)
    private String description;
    
    @Column(length = 1000)
    private String features;
    
    private Double latitude;
    private Double longitude;
    private String city;
    private String state;
    private String pincode;
    
    @NotNull
    private Long sellerId;
    
    @Builder.Default
    private boolean isFeatured = false;
    
    @Builder.Default
    private int viewCount = 0;
    
    private LocalDate insuranceExpiryDate;
    private LocalDate pucExpiryDate;
    private LocalDate fitnessExpiryDate;
}
