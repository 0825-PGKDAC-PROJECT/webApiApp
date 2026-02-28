package com.gaadix.analytics.service;

import com.gaadix.analytics.dto.DashboardStats;
import com.gaadix.analytics.dto.SalesAnalytics;
import com.gaadix.analytics.entity.AnalyticsEvent;
import com.gaadix.analytics.repository.AnalyticsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private AnalyticsRepository analyticsRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    void getDashboardStats_Success() {
        DashboardStats result = analyticsService.getDashboardStats();

        assertNotNull(result);
        assertTrue(result.getTotalUsers() >= 0);
        assertTrue(result.getTotalVehicles() >= 0);
    }

    @Test
    void getSalesAnalytics_Success() {
        SalesAnalytics result = analyticsService.getSalesAnalytics();

        assertNotNull(result);
        assertNotNull(result.getMonthlySales());
    }

    @Test
    void trackEvent_Success() {
        when(analyticsRepository.save(any(AnalyticsEvent.class))).thenReturn(new AnalyticsEvent());

        Map<String, String> metadata = new HashMap<>();
        metadata.put("userId", "1");
        metadata.put("vehicleId", "100");
        
        analyticsService.trackEvent("VEHICLE_VIEW", metadata);

        verify(analyticsRepository).save(any(AnalyticsEvent.class));
    }
}
