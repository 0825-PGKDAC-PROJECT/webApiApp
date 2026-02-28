package com.gaadix.inventory.dto;

import com.gaadix.common.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {
    
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private FuelType fuelType;
    private BigDecimal basePrice;
    private Integer odometer;
    private String color;
    private String registrationNumber;
    private OwnershipType ownershipType;
    private Transmission transmission;
    private CarStatus status;
    private String description;
    private String features;
    private String city;
    private String state;
    private Long sellerId;
    private boolean isFeatured;
    private int viewCount;
    private LocalDate insuranceExpiryDate;
    private List<String> imageUrls;
    private String primaryImageUrl;
    private Integer inspectionScore;
}
