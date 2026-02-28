package com.gaadix.fraud.controller;

import com.gaadix.common.dto.ApiResponse;
import com.gaadix.fraud.dto.FraudCheckRequest;
import com.gaadix.fraud.dto.FraudCheckResponse;
import com.gaadix.fraud.service.FraudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fraud")
@RequiredArgsConstructor
public class FraudController {
    
    private final FraudService fraudService;
    
    @PostMapping("/check")
    public ResponseEntity<ApiResponse<FraudCheckResponse>> checkFraud(
            @Valid @RequestBody FraudCheckRequest request) {
        FraudCheckResponse response = fraudService.checkFraud(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Fraud check completed"));
    }
    
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<ApiResponse<FraudCheckResponse>> getFraudCheck(
            @PathVariable Long transactionId) {
        FraudCheckResponse response = fraudService.getFraudCheck(transactionId);
        return ResponseEntity.ok(ApiResponse.success(response, "Fraud check retrieved"));
    }
}
