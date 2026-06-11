package com.naman.paymentsystem.enums;

public enum OrderState {
    CREATED("created"),
    CANCELLED("cancelled"),
    COMPLETED("completed");


    private final String label;

     OrderState(String label){
        this.label = label;
    }
}
