package com.naman.paymentsystem.service.impl;

import com.naman.paymentsystem.dto.MerchantRequestDto;
import com.naman.paymentsystem.dto.MerchantResponseDto;
import com.naman.paymentsystem.entity.Merchant;
import com.naman.paymentsystem.repository.MerchantRepository;
import com.naman.paymentsystem.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MerchantRepository merchantRepository;

    @Override
    public MerchantResponseDto.RegisterMerchant registerMerchant(MerchantRequestDto.RegisterMerchant registerMerchant) {

        validateMerchantRegistration(registerMerchant);
        Merchant merchant = new Merchant();
        merchant.setMerchantName(registerMerchant.getBusinessName());
        merchant.setApiKey("ak_" + UUID.randomUUID().toString().replace("-", ""));
        merchant.setSecretKey("sk_" + UUID.randomUUID().toString().replace("-", ""));
        Merchant saveMerchant = merchantRepository.save(merchant);
        merchant.setMerchantCode(saveMerchant.getId() + 100 + "");
        merchant.setEmail(registerMerchant.getEmail());
        merchant.setWebsite(registerMerchant.getWebsite());
        saveMerchant = merchantRepository.save(merchant);

        MerchantResponseDto.RegisterMerchant res = new MerchantResponseDto.RegisterMerchant();
        res.setId(saveMerchant.getId());
        res.setSecretKey(saveMerchant.getSecretKey());
        res.setName(saveMerchant.getMerchantName());
        res.setApikey(saveMerchant.getApiKey());
        res.setCode(saveMerchant.getMerchantCode());

        return res;
    }

    @Override
    public MerchantResponseDto.ValidateMerchantResponse LoginMerchant(MerchantRequestDto.ValidateMerchant validateMerchant) {

        Merchant merchant =
                merchantRepository
                        .findByApiKeyAndSecretKey(
                                validateMerchant.getApiKey(),
                                validateMerchant.getSecretKey())
                        .orElseThrow(() ->
                                new RuntimeException("Invalid Credentials"));

    }

    private void validateMerchantRegistration(MerchantRequestDto.RegisterMerchant registerMerchant) {


        if (registerMerchant.getBusinessName() == null || registerMerchant.getBusinessName().isEmpty()) {
            throw new RuntimeException("BusinessName is required");
        }

        if (registerMerchant.getEmail() == null || registerMerchant.getEmail().isEmpty() || !registerMerchant.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new RuntimeException("Invalid Email");
        }


        if (merchantRepository.existsByEmail(registerMerchant.getEmail())) {
            throw new RuntimeException("Merchant already exists");
        }

        if (merchantRepository.existsByMerchantName(registerMerchant.getBusinessName())) {
            throw new RuntimeException("Merchant already exists");
        }

    }
}
