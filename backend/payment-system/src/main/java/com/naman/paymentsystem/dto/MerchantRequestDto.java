package com.naman.paymentsystem.dto;

import lombok.Data;

public class MerchantRequestDto {

    @Data
    public static class RegisterMerchant {
        String BusinessName;
        String email;
        String website;
    }

    @Data
    public static class ValidateMerchant {
        private String apiKey;
        private String secretKey;
    }
}
