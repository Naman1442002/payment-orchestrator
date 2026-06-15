package com.naman.paymentsystem.dto;

import lombok.Builder;
import lombok.Data;

public class PciRequestDto {

    @Data
    public static class Tokenize {
        private Integer expiryMonth;
        private Integer expiryYear;
        private String cardHolderName;
        private String panNumber;
    }

    @Data
    @Builder
    public static class Detokenize {
        private String token;
    }

    @Data
    public static class cardDetails {
        private String token;
    }
}
