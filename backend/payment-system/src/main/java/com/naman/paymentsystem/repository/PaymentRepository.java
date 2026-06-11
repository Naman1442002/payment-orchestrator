package com.naman.paymentsystem.repository;

import com.naman.paymentsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "Select count(p)>0 From Payment p WHERE  p.merchantOrderId = :merchantOrderId")
    boolean existsByMerchantOrderId(String merchantOrderId);
}
