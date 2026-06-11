package com.naman.paymentsystem.dto;

import lombok.Builder;
import lombok.Data;

public class MerchantResponseDto {

    @Data
    public static class RegisterMerchant {
        long id;
        String name;
        String code;
        String apikey;
        String secretKey;
    }

    @Data
    @Builder
    public static class ValidateMerchantResponse {
        private Long merchantId;
        private String merchantCode;
        private String merchantName;
        private Boolean valid;
    }
}
