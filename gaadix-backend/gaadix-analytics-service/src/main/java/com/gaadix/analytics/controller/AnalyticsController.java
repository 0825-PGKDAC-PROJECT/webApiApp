package com.gaadix.analytics.controller;

import com.gaadix.analytics.dto.*;
import com.gaadix.analytics.service.AnalyticsService;
import com.gaadix.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardStats>> getDashboard() {
        DashboardStats stats = analyticsService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success("Dashboard stats retrieved", stats));
    }
    
    @GetMapping("/sales")
    public ResponseEntity<ApiResponse<SalesAnalytics>> getSalesAnalytics() {
        SalesAnalytics analytics = analyticsService.getSalesAnalytics();
        return ResponseEntity.ok(ApiResponse.success("Sales analytics retrieved", analytics));
    }
    
    @GetMapping("/popular-brands")
    public ResponseEntity<ApiResponse<PopularBrands>> getPopularBrands() {
        PopularBrands brands = analyticsService.getPopularBrands();
        return ResponseEntity.ok(ApiResponse.success("Popular brands retrieved", brands));
    }
}
