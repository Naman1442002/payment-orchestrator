package com.naman.paymentsystem.controller;

import com.naman.paymentsystem.constant.RestMappingConstant;
import com.naman.paymentsystem.dto.PaymentRequestDto;
import com.naman.paymentsystem.dto.PciRequestDto;
import com.naman.paymentsystem.service.PciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PciController {

    @Autowired
    PciService service;

    @PostMapping(path = RestMappingConstant.PciDSSUri.CREATE_PCI_TOKEN)
    public ResponseEntity<?> createPciDssToken(@RequestBody PciRequestDto.Tokenize req) {

        return ResponseEntity.ok(service.tokenize(req));

    }

    @PostMapping(path = RestMappingConstant.PciDSSUri.DETOKENIZE_CARD)
    public ResponseEntity<?> detokenizePciDssToken(@RequestBody PciRequestDto.Detokenize req) {

        return ResponseEntity.ok(service.deTokenize(req));

    }

    @GetMapping(path = RestMappingConstant.PciDSSUri.GET_CARD_DETAILS)
    public ResponseEntity<?> getCardDetails(@RequestBody PciRequestDto.cardDetails req) {
        return ResponseEntity.ok(service.getCardDetails(req));

    }


}
