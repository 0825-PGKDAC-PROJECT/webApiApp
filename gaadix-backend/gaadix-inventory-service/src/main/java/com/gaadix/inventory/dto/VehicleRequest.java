package com.gaadix.inventory.dto;

import com.gaadix.common.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequest {
    
    @NotBlank
    private String brand;
    
    @NotBlank
    private String model;
    
    @NotNull
    @Min(1990)
    private Integer year;
    
    @NotNull
    private FuelType fuelType;
    
    private Integer batteryCapacity;
    private Integer electricRange;
    
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal basePrice;
    
    @NotNull
    @Min(0)
    private Integer odometer;
    
    private String color;
    private String registrationNumber;
    private String registrationState;
    private String rtoCode;
    private OwnershipType ownershipType;
    private Transmission transmission;
    private String description;
    private String features;
    private String city;
    private String state;
    private String pincode;
    private LocalDate insuranceExpiryDate;
    private LocalDate pucExpiryDate;
}
