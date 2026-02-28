package com.gaadix.payment.service;

import com.gaadix.payment.dto.PaymentRequest;
import com.gaadix.payment.dto.PaymentResponse;
import com.gaadix.payment.entity.Payment;
import com.gaadix.payment.entity.Payment.PaymentStatus;
import com.gaadix.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void initiatePayment_Success() {
        PaymentRequest request = new PaymentRequest();
        request.setAmount(BigDecimal.valueOf(500000));
        
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setStatus(PaymentStatus.PENDING);
        
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponse result = paymentService.initiatePayment(request);

        assertNotNull(result);
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void getPaymentById_Success() {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setStatus(PaymentStatus.COMPLETED);
        
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        PaymentResponse result = paymentService.getPaymentById(1L);

        assertNotNull(result);
        verify(paymentRepository).findById(1L);
    }

    @Test
    void processPayment_Success() {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setStatus(PaymentStatus.PENDING);
        
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponse result = paymentService.processPayment(1L);

        assertNotNull(result);
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void completePayment_Success() {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setStatus(PaymentStatus.PROCESSING);
        
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponse result = paymentService.completePayment(1L);

        assertNotNull(result);
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void refundPayment_Success() {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setStatus(PaymentStatus.COMPLETED);
        
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentResponse result = paymentService.refundPayment(1L);

        assertNotNull(result);
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void refundPayment_NotCompleted_ThrowsException() {
        Payment payment = new Payment();
        payment.setStatus(PaymentStatus.PENDING);
        
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(payment));

        assertThrows(IllegalStateException.class, () -> paymentService.refundPayment(1L));
    }
}
