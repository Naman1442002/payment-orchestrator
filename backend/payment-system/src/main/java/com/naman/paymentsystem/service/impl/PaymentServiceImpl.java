package com.naman.paymentsystem.service.impl;

import com.naman.paymentsystem.dto.PaymentRequestDto;
import com.naman.paymentsystem.dto.PaymentResponseDto;
import com.naman.paymentsystem.dto.PciRequestDto;
import com.naman.paymentsystem.dto.PciResponseDto;
import com.naman.paymentsystem.entity.CardToken;
import com.naman.paymentsystem.entity.Merchant;
import com.naman.paymentsystem.entity.Payment;
import com.naman.paymentsystem.entity.Transaction;
import com.naman.paymentsystem.enums.PaymentMethod;
import com.naman.paymentsystem.enums.State;
import com.naman.paymentsystem.repository.CardTokenRepository;
import com.naman.paymentsystem.repository.MerchantRepository;
import com.naman.paymentsystem.repository.PaymentRepository;
import com.naman.paymentsystem.repository.TransactionRepository;
import com.naman.paymentsystem.service.PaymentService;
import com.naman.paymentsystem.service.PciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Autowired
    PaymentRepository paymentRepository;


    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    PciService pciService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CardTokenRepository cardTokenRepository;

    @Override
    public PaymentResponseDto.InitiatePayment InitiatePayment(PaymentRequestDto.InitiatePayment initiatePayment) {

        validatePayment(initiatePayment);


        Optional<Payment> existPayment = paymentRepository.findByMerchantOrderIdAndMerchantId(initiatePayment.getMerchantOrderId(), initiatePayment.getMerchantId());

        Payment payment = new Payment();
        String sessionId = UUID.randomUUID().toString();
        String redirectUrl = "https://payment.naman.com/payment-page?sessionId=";


        if (existPayment.isPresent()) {
            payment = existPayment.get();

            PaymentResponseDto.InitiatePayment initiatePaymentRes = new PaymentResponseDto.InitiatePayment();
            initiatePaymentRes.setPaymentId(payment.getId());
            initiatePaymentRes.setSessionId(payment.getSessionId());
            initiatePaymentRes.setState(payment.getPaymentStatus());
            initiatePaymentRes.setRedirectUrl(redirectUrl + payment.getSessionId());

            return initiatePaymentRes;
        }


        Optional<Merchant> merchant = merchantRepository.findById(initiatePayment.getMerchantId());
        payment.setPaymentStatus(State.INITIATED);
        payment.setCurrency(initiatePayment.getCurrency());
        payment.setAmount(initiatePayment.getAmount());
        payment.setMerchant(merchant.orElseThrow(() -> new RuntimeException("Merchant Not Found")));
        payment.setCustomerPhone(initiatePayment.getCustomerPhone());
        payment.setCustomerEmail(initiatePayment.getCustomerEmail());
        payment.setMerchantOrderId(initiatePayment.getMerchantOrderId());
        payment.setSessionId(sessionId);

        Payment savedPayment = paymentRepository.save(payment);

        PaymentResponseDto.InitiatePayment initiatePaymentRes = new PaymentResponseDto.InitiatePayment();
        initiatePaymentRes.setPaymentId(savedPayment.getId());
        initiatePaymentRes.setSessionId(savedPayment.getSessionId());
        initiatePaymentRes.setState(savedPayment.getPaymentStatus());
        initiatePaymentRes.setRedirectUrl(redirectUrl + savedPayment.getSessionId());
        return initiatePaymentRes;
    }

    private void validatePayment(PaymentRequestDto.InitiatePayment initiatePayment) {
        if (initiatePayment.getMerchantId() == null) {
            throw new RuntimeException("Merchant Id Required");
        }

        if (initiatePayment.getMerchantOrderId() == null || initiatePayment.getMerchantOrderId().isEmpty()) {
            throw new RuntimeException("Merchant Order Id Required");
        }

        if (initiatePayment.getAmount() == null || initiatePayment.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("Amount Can not Be Null or 0");
        }

        if (initiatePayment.getCurrency() == null || initiatePayment.getCurrency().isEmpty() || !Set.of("INR", "USD", "EUR").contains(initiatePayment.getCurrency().trim().toUpperCase())) {
            throw new RuntimeException("Invalid Currency Type");
        }

        if (initiatePayment.getCustomerEmail() == null || initiatePayment.getCustomerEmail().isEmpty() || !initiatePayment.getCustomerEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new RuntimeException("Invalid Email");
        }

        if (initiatePayment.getCustomerPhone() == null || initiatePayment.getCustomerPhone().isEmpty() || !initiatePayment.getCustomerPhone().matches("^\\+?[1-9]\\d{1,14}$")) {
            throw new RuntimeException("Invalid Phone");
        }

//        Optional<Merchant> isMerchantExist = merchantRepository.findById(initiatePayment.getMerchantId());
//
//        if (isMerchantExist.isEmpty()) {
//            throw new RuntimeException("Merchant Not Found");
//        }
//

//        Optional<Payment> isOrderExist = paymentRepository.findByMerchantOrderIdAndMerchantId(initiatePayment.getMerchantOrderId(), String.valueOf(initiatePayment.getMerchantId()));
//
//        if (isOrderExist.isPresent()) {
//            throw new RuntimeException("Merchant Order Already Exist");
//        }


    }


    @Override

    public PaymentResponseDto.ProcessPayment processPayment(
            PaymentRequestDto.ProcessPayment req) {

        if (req.getSessionId() == null || req.getSessionId().isBlank()) {
            throw new RuntimeException("Session Id Required");
        }

        if (req.getToken() == null || req.getToken().isBlank()) {
            throw new RuntimeException("Token Required");
        }


//        1. get payment by session key

        Payment payment = paymentRepository
                .findBySessionId(req.getSessionId())
                .orElseThrow(() -> new RuntimeException("Payment Not Found"));

//        2. Get Card Token with token

        CardToken cardToken = cardTokenRepository
                .findByToken(req.getToken())
                .orElseThrow(() -> new RuntimeException("Card Details Not Found "));

//        3. prepare transaction set id , status ,

        Transaction transaction = Transaction.builder()
                .paymentMethod(PaymentMethod.CARD)
                .gatewayTransactionId("GT_" + UUID.randomUUID().toString()
                        .replace("-", ""))
                .bankReferenceNumber("BNK_" + UUID.randomUUID().toString()
                        .replace("-", ""))
                .payment(payment)
                .paymentStatus(State.PENDING)
                .build();

//        4. update payment
        payment.setPaymentStatus(State.PENDING);


//        5. save transaction and payment

        Transaction savedtransaction = transactionRepository.save(transaction);
        Payment savedPayment = paymentRepository.save(payment);


//        6. return transaction response

        return PaymentResponseDto.ProcessPayment
                .builder()
                .paymentId(savedPayment.getId())
                .transactionId(String.valueOf(savedtransaction.getId()))
                .status(State.PENDING)
                .message("Payment Processing")
                .build();

    }
}
