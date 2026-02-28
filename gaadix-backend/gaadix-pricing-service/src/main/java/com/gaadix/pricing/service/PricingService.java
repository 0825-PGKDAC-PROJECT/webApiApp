package com.gaadix.pricing.service;

import com.gaadix.pricing.dto.PriceEstimateRequest;
import com.gaadix.pricing.dto.PriceEstimateResponse;
import com.gaadix.pricing.entity.PriceEstimate;
import com.gaadix.pricing.repository.PriceEstimateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PricingService {
    
    private final PriceEstimateRepository repository;
    private static final BigDecimal DEPRECIATION_RATE = new BigDecimal("0.15");
    private static final BigDecimal MARKET_TREND_FACTOR = new BigDecimal("0.05");
    
    @Transactional
    public PriceEstimateResponse calculatePrice(PriceEstimateRequest request) {
        int vehicleAge = LocalDate.now().getYear() - request.getYear();
        
        BigDecimal depreciationFactor = BigDecimal.ONE.subtract(
                DEPRECIATION_RATE.multiply(new BigDecimal(vehicleAge))
        );
        
        if (depreciationFactor.compareTo(BigDecimal.ZERO) < 0) {
            depreciationFactor = new BigDecimal("0.10");
        }
        
        BigDecimal baseEstimate = request.getOriginalPrice().multiply(depreciationFactor);
        
        BigDecimal odometerFactor = calculateOdometerFactor(request.getOdometer());
        baseEstimate = baseEstimate.multiply(odometerFactor);
        
        BigDecimal marketAdjustment = baseEstimate.multiply(MARKET_TREND_FACTOR);
        BigDecimal estimatedPrice = baseEstimate.add(marketAdjustment);
        
        BigDecimal minPrice = estimatedPrice.multiply(new BigDecimal("0.90"));
        BigDecimal maxPrice = estimatedPrice.multiply(new BigDecimal("1.10"));
        
        PriceEstimate estimate = PriceEstimate.builder()
                .carId(request.getCarId())
                .brand(request.getBrand())
                .model(request.getModel())
                .year(request.getYear())
                .odometer(request.getOdometer())
                .originalPrice(request.getOriginalPrice())
                .estimatedPrice(estimatedPrice.setScale(2, RoundingMode.HALF_UP))
                .minPrice(minPrice.setScale(2, RoundingMode.HALF_UP))
                .maxPrice(maxPrice.setScale(2, RoundingMode.HALF_UP))
                .depreciationRate(DEPRECIATION_RATE)
                .marketTrendFactor(MARKET_TREND_FACTOR)
                .pricingRemarks("Calculated based on age, odometer, and market trends")
                .build();
        
        PriceEstimate saved = repository.save(estimate);
        return mapToResponse(saved);
    }
    
    @Transactional(readOnly = true)
    public PriceEstimateResponse getEstimate(Long carId) {
        PriceEstimate estimate = repository.findByCarId(carId)
                .orElseThrow(() -> new RuntimeException("Price estimate not found"));
        return mapToResponse(estimate);
    }
    
    private BigDecimal calculateOdometerFactor(Integer odometer) {
        if (odometer < 20000) return new BigDecimal("1.00");
        if (odometer < 50000) return new BigDecimal("0.95");
        if (odometer < 80000) return new BigDecimal("0.90");
        if (odometer < 120000) return new BigDecimal("0.85");
        return new BigDecimal("0.80");
    }
    
    private PriceEstimateResponse mapToResponse(PriceEstimate estimate) {
        return PriceEstimateResponse.builder()
                .id(estimate.getId())
                .carId(estimate.getCarId())
                .brand(estimate.getBrand())
                .model(estimate.getModel())
                .year(estimate.getYear())
                .odometer(estimate.getOdometer())
                .originalPrice(estimate.getOriginalPrice())
                .estimatedPrice(estimate.getEstimatedPrice())
                .minPrice(estimate.getMinPrice())
                .maxPrice(estimate.getMaxPrice())
                .depreciationRate(estimate.getDepreciationRate())
                .marketTrendFactor(estimate.getMarketTrendFactor())
                .pricingRemarks(estimate.getPricingRemarks())
                .build();
    }
}
