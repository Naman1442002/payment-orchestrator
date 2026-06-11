package com.naman.paymentsystem.enums;

public enum Role {
    MERCHANT("merchant"),
    CUSTOMER("customer");
    private final String label;

    Role(String label){
        this.label= label;
    }
}
