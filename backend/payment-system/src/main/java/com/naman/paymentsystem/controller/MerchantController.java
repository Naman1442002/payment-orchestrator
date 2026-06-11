package com.naman.paymentsystem.controller;

import com.naman.paymentsystem.constant.RestMappingConstant;
import com.naman.paymentsystem.dto.MerchantRequestDto;
import com.naman.paymentsystem.service.MerchantService;
import com.naman.paymentsystem.service.impl.MerchantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    @PostMapping(path = RestMappingConstant.MerchantUri.REGISTER_MERCHANT)
    public ResponseEntity<?> RegisterMerchant(@RequestBody MerchantRequestDto.RegisterMerchant registerMerchant) {
        return ResponseEntity.ok(merchantService.registerMerchant(registerMerchant));
    }
}
