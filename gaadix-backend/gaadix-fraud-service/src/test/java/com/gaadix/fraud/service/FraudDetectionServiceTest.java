package com.gaadix.fraud.service;

import com.gaadix.fraud.dto.FraudCheckRequest;
import com.gaadix.fraud.dto.FraudCheckResponse;
import com.gaadix.fraud.entity.FraudCheck;
import com.gaadix.fraud.repository.FraudCheckRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FraudDetectionServiceTest {

    @Mock
    private FraudCheckRepository repository;

    @InjectMocks
    private FraudService fraudService;

    @Test
    void testCheckFraud_LowRisk() {
        FraudCheckRequest request = new FraudCheckRequest();
        request.setUserId(1L);
        request.setTransactionId(100L);
        request.setAmount(new BigDecimal("200000"));

        FraudCheck fraudCheck = new FraudCheck();
        fraudCheck.setRiskScore(10);
        when(repository.save(any())).thenReturn(fraudCheck);
        when(repository.countByUserId(1L)).thenReturn(0L);
        when(repository.countRecentTransactionsByUser(1L)).thenReturn(0L);

        FraudCheckResponse response = fraudService.checkFraud(request);
        assertThat(response).isNotNull();
    }

    @Test
    void testCalculateRiskScore() {
        FraudCheckRequest request = new FraudCheckRequest();
        request.setUserId(1L);
        request.setTransactionId(100L);
        request.setAmount(new BigDecimal("1500000"));

        FraudCheck fraudCheck = new FraudCheck();
        fraudCheck.setRiskScore(30);
        when(repository.save(any())).thenReturn(fraudCheck);
        when(repository.countByUserId(1L)).thenReturn(0L);
        when(repository.countRecentTransactionsByUser(1L)).thenReturn(0L);

        FraudCheckResponse response = fraudService.checkFraud(request);
        assertThat(response.getRiskScore()).isGreaterThan(0);
    }
}
