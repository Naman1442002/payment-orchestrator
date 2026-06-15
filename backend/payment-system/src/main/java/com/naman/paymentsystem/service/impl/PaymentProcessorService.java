package com.naman.paymentsystem.service.impl;

import com.naman.paymentsystem.entity.Payment;
import com.naman.paymentsystem.entity.Transaction;
import com.naman.paymentsystem.enums.State;
import com.naman.paymentsystem.repository.PaymentRepository;
import com.naman.paymentsystem.repository.TransactionRepository;
import com.naman.paymentsystem.service.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentProcessorService implements PaymentProcessor {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void processTransaction(Long transactionId) {

        try {

            Thread.sleep(5000);
            Transaction transaction =
                    transactionRepository.findById(transactionId)
                            .orElseThrow();

            Payment payment = transaction.getPayment();

            boolean success = Math.random() > 0.3;

            if (success) {

                transaction.setPaymentStatus(State.SUCCESS);
                payment.setPaymentStatus(State.SUCCESS);

            } else {

                transaction.setPaymentStatus(State.FAILED);
                transaction.setFailureReason("Bank Declined");

                payment.setPaymentStatus(State.FAILED);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
