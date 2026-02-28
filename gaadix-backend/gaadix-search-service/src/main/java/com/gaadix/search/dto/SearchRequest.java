package com.gaadix.search.dto;

import com.gaadix.common.enums.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchRequest {
    
    private String keyword;
    private String brand;
    private String model;
    private Integer minYear;
    private Integer maxYear;
    private FuelType fuelType;
    private Transmission transmission;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer maxOdometer;
    private String city;
    private String state;
    private Double latitude;
    private Double longitude;
    private Integer radiusKm;
    private CarStatus status;
    
    @Builder.Default
    private int page = 0;
    
    @Builder.Default
    private int size = 20;
    
    @Builder.Default
    private String sortBy = "createdAt";
    
    @Builder.Default
    private String sortDirection = "DESC";
}
