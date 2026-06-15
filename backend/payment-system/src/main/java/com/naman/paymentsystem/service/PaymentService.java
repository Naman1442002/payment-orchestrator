package com.naman.paymentsystem.service;

import com.naman.paymentsystem.dto.PaymentRequestDto;
import com.naman.paymentsystem.dto.PaymentResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    public PaymentResponseDto.InitiatePayment InitiatePayment(PaymentRequestDto.InitiatePayment initiatePayment);

    PaymentResponseDto.ProcessPayment processPayment(
            PaymentRequestDto.ProcessPayment req);
}
