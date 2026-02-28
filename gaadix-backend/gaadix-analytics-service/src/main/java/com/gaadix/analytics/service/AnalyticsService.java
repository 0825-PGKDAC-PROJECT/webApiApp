package com.gaadix.analytics.service;

import com.gaadix.analytics.dto.*;
import com.gaadix.analytics.entity.AnalyticsEvent;
import com.gaadix.analytics.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    
    private final AnalyticsRepository repository;
    
    @Transactional(readOnly = true)
    public DashboardStats getDashboardStats() {
        long totalUsers = 1250L;
        long totalVehicles = 850L;
        long totalTransactions = 320L;
        BigDecimal totalRevenue = new BigDecimal("45000000");
        
        return DashboardStats.builder()
                .totalUsers(totalUsers)
                .totalVehicles(totalVehicles)
                .totalTransactions(totalTransactions)
                .totalRevenue(totalRevenue)
                .activeListings(totalVehicles - 120)
                .pendingVerifications(45L)
                .build();
    }
    
    @Transactional(readOnly = true)
    public SalesAnalytics getSalesAnalytics() {
        Map<String, Long> monthlySales = new LinkedHashMap<>();
        monthlySales.put("Jan", 25L);
        monthlySales.put("Feb", 32L);
        monthlySales.put("Mar", 28L);
        monthlySales.put("Apr", 35L);
        monthlySales.put("May", 42L);
        monthlySales.put("Jun", 38L);
        
        return SalesAnalytics.builder()
                .monthlySales(monthlySales)
                .averagePrice(new BigDecimal("650000"))
                .totalSales(200L)
                .growthRate(15.5)
                .build();
    }
    
    @Transactional(readOnly = true)
    public PopularBrands getPopularBrands() {
        Map<String, Long> brandCounts = new LinkedHashMap<>();
        brandCounts.put("Maruti Suzuki", 180L);
        brandCounts.put("Hyundai", 145L);
        brandCounts.put("Honda", 120L);
        brandCounts.put("Tata", 95L);
        brandCounts.put("Mahindra", 85L);
        
        return PopularBrands.builder()
                .brandCounts(brandCounts)
                .topBrand("Maruti Suzuki")
                .totalBrands(15L)
                .build();
    }
    
    @Transactional
    public void trackEvent(String eventType, Map<String, String> metadata) {
        AnalyticsEvent event = AnalyticsEvent.builder()
                .eventType(eventType)
                .metadata(metadata.toString())
                .build();
        repository.save(event);
    }
}
