package com.naman.paymentsystem.dto;

import com.naman.paymentsystem.enums.State;
import lombok.Builder;
import lombok.Data;

public class PaymentResponseDto {

    @Data
    public static class InitiatePayment {
        Long paymentId;
        String sessionId;
        String redirectUrl;
        State state;
    }

    @Data
    @Builder
    public static class ProcessPayment {
        private Long paymentId;
        private String transactionId;
        private State status;
        private String message;
    }
}
