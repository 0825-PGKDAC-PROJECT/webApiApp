package com.gaadix.pricing.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceEstimateResponse {
    
    private Long id;
    private Long carId;
    private String brand;
    private String model;
    private Integer year;
    private Integer odometer;
    private BigDecimal originalPrice;
    private BigDecimal estimatedPrice;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal depreciationRate;
    private BigDecimal marketTrendFactor;
    private String pricingRemarks;
}
