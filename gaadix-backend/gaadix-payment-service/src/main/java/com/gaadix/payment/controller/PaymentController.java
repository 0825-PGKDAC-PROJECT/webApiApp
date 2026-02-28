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
                .body(ApiResponse.success("Payment initiated successfully", response));
    }
    
    @PostMapping("/{id}/process")
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(@PathVariable Long id) {
        PaymentResponse response = paymentService.processPayment(id);
        return ResponseEntity.ok(ApiResponse.success("Payment processing started", response));
    }
    
    @PostMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<PaymentResponse>> completePayment(@PathVariable Long id) {
        PaymentResponse response = paymentService.completePayment(id);
        return ResponseEntity.ok(ApiResponse.success("Payment completed successfully", response));
    }
    
    @PostMapping("/{id}/refund")
    public ResponseEntity<ApiResponse<PaymentResponse>> refundPayment(@PathVariable Long id) {
        PaymentResponse response = paymentService.refundPayment(id);
        return ResponseEntity.ok(ApiResponse.success("Payment refunded successfully", response));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(@PathVariable Long id) {
        PaymentResponse response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.success("Payment retrieved successfully", response));
    }
    
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByBuyer(
            @PathVariable Long buyerId) {
        List<PaymentResponse> response = paymentService.getPaymentsByBuyer(buyerId);
        return ResponseEntity.ok(ApiResponse.success("Buyer payments retrieved successfully", response));
    }
    
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsBySeller(
            @PathVariable Long sellerId) {
        List<PaymentResponse> response = paymentService.getPaymentsBySeller(sellerId);
        return ResponseEntity.ok(ApiResponse.success("Seller payments retrieved successfully", response));
    }
}
