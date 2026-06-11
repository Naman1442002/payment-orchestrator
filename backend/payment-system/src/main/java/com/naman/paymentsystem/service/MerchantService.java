package com.naman.paymentsystem.service;

import com.naman.paymentsystem.dto.MerchantRequestDto;
import com.naman.paymentsystem.dto.MerchantResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface MerchantService {
    public MerchantResponseDto.RegisterMerchant registerMerchant(MerchantRequestDto.RegisterMerchant registerMerchant);

    MerchantResponseDto.ValidateMerchantResponse LoginMerchant(MerchantRequestDto.ValidateMerchant validateMerchant);
}
