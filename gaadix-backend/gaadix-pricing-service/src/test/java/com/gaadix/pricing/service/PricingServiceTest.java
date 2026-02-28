package com.gaadix.pricing.service;

import com.gaadix.pricing.dto.PriceEstimateRequest;
import com.gaadix.pricing.dto.PriceEstimateResponse;
import com.gaadix.pricing.entity.PriceEstimate;
import com.gaadix.pricing.repository.PriceEstimateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricingServiceTest {

    @Mock
    private PriceEstimateRepository repository;

    @InjectMocks
    private PricingService pricingService;

    @Test
    void testCalculatePrice() {
        PriceEstimateRequest request = new PriceEstimateRequest();
        request.setCarId(1L);
        request.setBrand("Toyota");
        request.setModel("Camry");
        request.setYear(2020);
        request.setOdometer(30000);
        request.setOriginalPrice(new BigDecimal("2500000"));

        PriceEstimate estimate = new PriceEstimate();
        estimate.setId(1L);
        estimate.setCarId(1L);
        estimate.setEstimatedPrice(new BigDecimal("2000000"));
        when(repository.save(any())).thenReturn(estimate);

        PriceEstimateResponse response = pricingService.calculatePrice(request);
        assertThat(response).isNotNull();
    }

    @Test
    void testGetEstimate() {
        PriceEstimate estimate = new PriceEstimate();
        estimate.setId(1L);
        estimate.setCarId(1L);
        estimate.setEstimatedPrice(new BigDecimal("2000000"));
        when(repository.findByCarId(1L)).thenReturn(Optional.of(estimate));

        PriceEstimateResponse response = pricingService.getEstimate(1L);
        assertThat(response).isNotNull();
    }
}
