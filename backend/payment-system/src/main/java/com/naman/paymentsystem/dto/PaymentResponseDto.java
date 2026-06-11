package com.naman.paymentsystem.dto;

import com.naman.paymentsystem.enums.State;
import lombok.Data;

public class PaymentResponseDto {

    @Data
    public static class InitiatePayment {
        Long paymentId;
        String sessionId;
        String redirectUrl;
        State state;
    }
}
