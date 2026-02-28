package com.gaadix.pricing.entity;

import com.gaadix.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "price_estimates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceEstimate extends BaseEntity {
    
    @Column(nullable = false)
    private Long carId;
    
    @Column(nullable = false)
    private String brand;
    
    @Column(nullable = false)
    private String model;
    
    @Column(nullable = false)
    private Integer year;
    
    @Column(nullable = false)
    private Integer odometer;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal originalPrice;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedPrice;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minPrice;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal maxPrice;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal depreciationRate;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal marketTrendFactor;
    
    private String pricingRemarks;
}
