package com.naman.paymentsystem.service.impl;

import com.naman.paymentsystem.dto.PaymentRequestDto;
import com.naman.paymentsystem.dto.PaymentResponseDto;
import com.naman.paymentsystem.entity.Merchant;
import com.naman.paymentsystem.entity.Payment;
import com.naman.paymentsystem.enums.State;
import com.naman.paymentsystem.repository.MerchantRepository;
import com.naman.paymentsystem.repository.PaymentRepository;
import com.naman.paymentsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Autowired
    PaymentRepository paymentRepository;


    @Autowired
    MerchantRepository merchantRepository;

    @Override
    public PaymentResponseDto.InitiatePayment InitiatePayment(PaymentRequestDto.InitiatePayment initiatePayment) {


        validatePayment(initiatePayment);
        Payment payment = new Payment();

        String sessionId = UUID.randomUUID().toString();
        String redirectUrl = "https://payment.naman.com/payment-page?sessionId=" + sessionId;

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
        initiatePaymentRes.setRedirectUrl(redirectUrl);
        return initiatePaymentRes;
    }

    private void validatePayment(PaymentRequestDto.InitiatePayment initiatePayment) {
        if (initiatePayment.getMerchantId() == null) {
            throw new RuntimeException("Merchant Id Required");
        }

        if (initiatePayment.getMerchantOrderId() == null || initiatePayment.getMerchantOrderId().isEmpty()) {
            throw new RuntimeException("Merchant Order Id Required");
        }

        if (initiatePayment.getAmount() == null || initiatePayment.getAmount().compareTo(BigDecimal.valueOf(0)) == 0) {
            throw new RuntimeException("Amount Can not Be Null or 0");
        }

        if (initiatePayment.getCurrency() == null || initiatePayment.getCurrency().isEmpty() || !"INR,USD,EUR".toLowerCase().contains(initiatePayment.getCurrency().toLowerCase())) {
            throw new RuntimeException("Invalid Currency Type");
        }

        if (initiatePayment.getCustomerEmail() == null || initiatePayment.getCustomerEmail().isEmpty() || !initiatePayment.getCustomerEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new RuntimeException("Invalid Email");
        }

        if (initiatePayment.getCustomerPhone() == null || initiatePayment.getCustomerPhone().isEmpty() || !initiatePayment.getCustomerPhone().matches("^\\+?[1-9]\\d{1,14}$")) {
            throw new RuntimeException("Invalid Phone");
        }

        boolean isMerchantExist = merchantRepository.existsById(initiatePayment.getMerchantId());

        if (!isMerchantExist) {
            throw new RuntimeException("Merchant Not Found");
        }


        boolean isOrderExist = paymentRepository.existsByMerchantOrderId(initiatePayment.getMerchantOrderId());

        if (isOrderExist) {
            throw new RuntimeException("Merchant Order Already Exist");
        }


    }
}
