package com.naman.paymentsystem.enums;


public enum PaymentMethod {

    UPI("upi"),
    CARD("card"),
    NET_BANKING("net_banking"),
    WALLET("wallet");

    private final String label;

    PaymentMethod(String label) {
        this.label = label;
    }
}
