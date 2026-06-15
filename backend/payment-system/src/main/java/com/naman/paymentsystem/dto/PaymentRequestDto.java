package com.naman.paymentsystem.dto;

import com.naman.paymentsystem.entity.CardToken;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDto {

    @Data
    public static class InitiatePayment {

        private Long merchantId;

        private String merchantOrderId;

        private BigDecimal amount;

        private String currency;

        private String customerEmail;

        private String customerPhone;
    }

    @Data
    public static class ProcessPayment {
        private String sessionId;
        private String token;
    }

    @Data
    public static class WebhookPayment {
        private String gatewayTxnId;
        private String status;
    }
}
