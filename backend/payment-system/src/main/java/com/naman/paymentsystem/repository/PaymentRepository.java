package com.naman.paymentsystem.repository;

import com.naman.paymentsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "Select count(p)>0 From Payment p WHERE  p.merchantOrderId = :merchantOrderId")
    boolean existsByMerchantOrderId(String merchantOrderId);

    @Query(value = "Select p From Payment p WHERE  p.merchantOrderId = :merchantOrderId and p.merchant.id = :merchantId")
    Optional<Payment> findByMerchantOrderIdAndMerchantId(String merchantOrderId, Long merchantId);

    @Query(value = "Select p From Payment p WHERE  p.sessionId = :sessionId  ")
    Optional<Payment> findBySessionId(String sessionId);

    @Query(value = "Select p From Payment p WHERE  p.sessionId = :sessionId  ")
    Optional<Payment> findBySessionId(String sessionId);
}
