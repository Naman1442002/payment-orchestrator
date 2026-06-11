package com.naman.paymentsystem.constant;

public interface RestMappingConstant {

    String BaseUrl = "/payment-system";

    public interface PaymentUri {

        String PAYMENT_INITIATED = BaseUrl + "/payment-initiated";
        String PAYMENT_PROCESS = BaseUrl + "/payment-process";
        String GET_PAYMENT_BY_ID = BaseUrl + "/{id}";
        String GET_PAYMENT_STATUS_BY_ID = BaseUrl + "/status/{id}";
        String PAYMENT_WEBHOOK = BaseUrl + "/payment-webhook";

    }

    public interface MerchantUri {
        String REGISTER_MERCHANT = BaseUrl + "/register-merchant";

    }

    public interface PciDSSUri {

        String CREATE_PCI_TOKEN = BaseUrl + "/tokenize";

        String DETOKENIZE_CARD = BaseUrl + "/detokenize";

        String GET_CARD_DETAILS = BaseUrl + "/card/{token}";
    }
}
