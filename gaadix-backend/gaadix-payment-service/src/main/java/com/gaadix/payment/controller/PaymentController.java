package com.gaadix.payment.controller;

import com.gaadix.common.dto.ApiResponse;
import com.gaadix.payment.dto.PaymentRequest;
import com.gaadix.payment.dto.PaymentResponse;
import com.gaadix.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    
    private final PaymentService paymentService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> initiatePayment(
            @Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.initiatePayment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Payment initiated successfully"));
    }
    
    @PostMapping("/{id}/process")
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(@PathVariable Long id) {
        PaymentResponse response = paymentService.processPayment(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Payment processing started"));
    }
    
    @PostMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<PaymentResponse>> completePayment(@PathVariable Long id) {
        PaymentResponse response = paymentService.completePayment(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Payment completed successfully"));
    }
    
    @PostMapping("/{id}/refund")
    public ResponseEntity<ApiResponse<PaymentResponse>> refundPayment(@PathVariable Long id) {
        PaymentResponse response = paymentService.refundPayment(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Payment refunded successfully"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(@PathVariable Long id) {
        PaymentResponse response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Payment retrieved successfully"));
    }
    
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByBuyer(
            @PathVariable Long buyerId) {
        List<PaymentResponse> response = paymentService.getPaymentsByBuyer(buyerId);
        return ResponseEntity.ok(ApiResponse.success(response, "Buyer payments retrieved successfully"));
    }
    
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsBySeller(
            @PathVariable Long sellerId) {
        List<PaymentResponse> response = paymentService.getPaymentsBySeller(sellerId);
        return ResponseEntity.ok(ApiResponse.success(response, "Seller payments retrieved successfully"));
    }
}
