package com.naman.paymentsystem.enums;


public enum State {
    INITIATED("initiated"), SUCCESS("success"), FAILED("failed"),
    PENDING("pending"), REFUNDED("refunded");

    private final String label;

    State(String label) {
        this.label = label;
    }
}
