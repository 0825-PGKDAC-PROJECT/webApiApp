package com.gaadix.payment.repository;

import com.gaadix.payment.entity.Payment;
import com.gaadix.payment.entity.Payment.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByTransactionId(Long transactionId);
    
    List<Payment> findByBuyerId(Long buyerId);
    
    List<Payment> findBySellerId(Long sellerId);
    
    Page<Payment> findByStatus(PaymentStatus status, Pageable pageable);
    
    List<Payment> findByCarId(Long carId);
    
    Optional<Payment> findByGatewayTransactionId(String gatewayTransactionId);
}
