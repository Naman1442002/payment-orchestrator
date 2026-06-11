package com.naman.paymentsystem.controller;

import com.naman.paymentsystem.constant.RestMappingConstant;
import com.naman.paymentsystem.dto.PaymentRequestDto;
import com.naman.paymentsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    @Autowired
    PaymentService service;

    @PostMapping(path = RestMappingConstant.PaymentUri.PAYMENT_INITIATED)
    public ResponseEntity<?> InitiatePayment(@RequestBody PaymentRequestDto.InitiatePayment req) {

        return ResponseEntity.ok(service.InitiatePayment(req));

    }

    @PostMapping(path = RestMappingConstant.PaymentUri.PAYMENT_PROCESS)
    public ResponseEntity<?> ProcessPayment(@RequestBody PaymentRequestDto.ProcessPayment req) {
        return ResponseEntity.ok("Process Payment");

    }

    @PostMapping(path = RestMappingConstant.PaymentUri.PAYMENT_WEBHOOK)
    public ResponseEntity<?> PaymentWebhook(@RequestBody(required = true) PaymentRequestDto.WebhookPayment req) {
        return ResponseEntity.ok("Payment Webhook");

    }

    @GetMapping(path = RestMappingConstant.PaymentUri.GET_PAYMENT_BY_ID)
    public ResponseEntity<?> GetPaymentById(@PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.ok("Payment By Id ");

    }

    @GetMapping(path = RestMappingConstant.PaymentUri.GET_PAYMENT_STATUS_BY_ID)
    public ResponseEntity<?> PaymentStatusById(@PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.ok("Payment Status By Id ");

    }

}
