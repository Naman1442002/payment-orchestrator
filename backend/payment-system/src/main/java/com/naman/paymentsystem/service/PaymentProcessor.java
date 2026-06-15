package com.naman.paymentsystem.service;

public interface PaymentProcessor {
    public void processTransaction(Long transactionId);
}
