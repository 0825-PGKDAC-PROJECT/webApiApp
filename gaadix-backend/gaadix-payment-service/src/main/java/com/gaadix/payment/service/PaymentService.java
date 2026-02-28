package com.gaadix.payment.service;

import com.gaadix.common.exception.ResourceNotFoundException;
import com.gaadix.payment.dto.PaymentRequest;
import com.gaadix.payment.dto.PaymentResponse;
import com.gaadix.payment.entity.Payment;
import com.gaadix.payment.entity.Payment.PaymentStatus;
import com.gaadix.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    
    @Transactional
    public PaymentResponse initiatePayment(PaymentRequest request) {
        Payment payment = Payment.builder()
                .transactionId(request.getTransactionId())
                .carId(request.getCarId())
                .buyerId(request.getBuyerId())
                .sellerId(request.getSellerId())
                .amount(request.getAmount())
                .method(request.getMethod())
                .status(PaymentStatus.PENDING)
                .remarks(request.getRemarks())
                .build();
        
        Payment saved = paymentRepository.save(payment);
        return mapToResponse(saved);
    }
    
    @Transactional
    public PaymentResponse processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        
        payment.setStatus(PaymentStatus.PROCESSING);
        payment.setGatewayTransactionId("GW-" + UUID.randomUUID().toString());
        
        Payment updated = paymentRepository.save(payment);
        return mapToResponse(updated);
    }
    
    @Transactional
    public PaymentResponse completePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaidAt(LocalDateTime.now());
        
        Payment updated = paymentRepository.save(payment);
        return mapToResponse(updated);
    }
    
    @Transactional
    public PaymentResponse refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        
        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Only completed payments can be refunded");
        }
        
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setRefundedAt(LocalDateTime.now());
        
        Payment updated = paymentRepository.save(payment);
        return mapToResponse(updated);
    }
    
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        return mapToResponse(payment);
    }
    
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByBuyer(Long buyerId) {
        return paymentRepository.findByBuyerId(buyerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsBySeller(Long sellerId) {
        return paymentRepository.findBySellerId(sellerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .transactionId(payment.getTransactionId())
                .carId(payment.getCarId())
                .buyerId(payment.getBuyerId())
                .sellerId(payment.getSellerId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .method(payment.getMethod())
                .gatewayTransactionId(payment.getGatewayTransactionId())
                .paidAt(payment.getPaidAt())
                .refundedAt(payment.getRefundedAt())
                .remarks(payment.getRemarks())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
