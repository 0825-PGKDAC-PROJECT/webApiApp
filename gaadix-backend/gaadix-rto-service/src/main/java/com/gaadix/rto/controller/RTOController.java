package com.gaadix.rto.controller;

import com.gaadix.common.dto.ApiResponse;
import com.gaadix.rto.dto.RTOVerificationRequest;
import com.gaadix.rto.dto.RTOVerificationResponse;
import com.gaadix.rto.service.RTOService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rto")
@RequiredArgsConstructor
public class RTOController {
    
    private final RTOService rtoService;
    
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<RTOVerificationResponse>> verifyVehicle(
            @Valid @RequestBody RTOVerificationRequest request) {
        RTOVerificationResponse response = rtoService.verifyVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Verification initiated", response));
    }
    
    @GetMapping("/verify/{registrationNumber}")
    public ResponseEntity<ApiResponse<RTOVerificationResponse>> getVerification(
            @PathVariable String registrationNumber) {
        RTOVerificationResponse response = rtoService.getVerification(registrationNumber);
        return ResponseEntity.ok(ApiResponse.success("Verification retrieved", response));
    }
}
