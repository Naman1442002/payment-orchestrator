package com.naman.paymentsystem.dto;

import lombok.*;
import org.springframework.stereotype.Service;

public class PciResponseDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Tokenize {
        String token;
    }

    @Data
    @Builder
    public static class Detokenize {
        private Integer expiryMonth;
        private Integer expiryYear;
        private String cardHolderName;
        private String panNumber;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardDetailsResponse {

        private String token;
        private String cardType;
        private String last4;

        private Integer expiryMonth;
        private Integer expiryYear;

        private String cardHolderName;
    }
}
