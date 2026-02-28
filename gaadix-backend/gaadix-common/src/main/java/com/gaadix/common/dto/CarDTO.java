package com.gaadix.common.dto;

import com.gaadix.common.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDTO {
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private FuelType fuelType;
    private Integer batteryCapacity;
    private Integer electricRange;
    private Integer fuelTankCapacity;
    private Double mileageCity;
    private Double mileageHighway;
    private BigDecimal basePrice;
    private Integer odometer;
    private String color;
    private String registrationNumber;
    private String registrationState;
    private String rtoCode;
    private OwnershipType ownershipType;
    private Transmission transmission;
    private CarStatus status;
    private String description;
    private String features;
    private Double latitude;
    private Double longitude;
    private String city;
    private String state;
    private String pincode;
    private Long sellerId;
    private boolean isFeatured;
    private int viewCount;
    private LocalDate insuranceExpiryDate;
    private LocalDate pucExpiryDate;
    private LocalDate fitnessExpiryDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
