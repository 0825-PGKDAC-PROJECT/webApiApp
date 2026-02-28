package com.gaadix.pricing.controller;

import com.gaadix.common.dto.ApiResponse;
import com.gaadix.pricing.dto.PriceEstimateRequest;
import com.gaadix.pricing.dto.PriceEstimateResponse;
import com.gaadix.pricing.service.PricingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pricing")
@RequiredArgsConstructor
public class PricingController {
    
    private final PricingService pricingService;
    
    @PostMapping("/estimate")
    public ResponseEntity<ApiResponse<PriceEstimateResponse>> calculatePrice(
            @Valid @RequestBody PriceEstimateRequest request) {
        PriceEstimateResponse response = pricingService.calculatePrice(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Price estimated successfully"));
    }
    
    @GetMapping("/estimate/{carId}")
    public ResponseEntity<ApiResponse<PriceEstimateResponse>> getEstimate(
            @PathVariable Long carId) {
        PriceEstimateResponse response = pricingService.getEstimate(carId);
        return ResponseEntity.ok(ApiResponse.success(response, "Estimate retrieved successfully"));
    }
}
