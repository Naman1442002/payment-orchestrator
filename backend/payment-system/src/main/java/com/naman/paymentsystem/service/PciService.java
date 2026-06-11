package com.naman.paymentsystem.service;

import com.naman.paymentsystem.dto.PciRequestDto;
import com.naman.paymentsystem.dto.PciResponseDto;

public interface PciService {

    public PciResponseDto.Tokenize tokenize(PciRequestDto.Tokenize req);

    public PciResponseDto.Detokenize deTokenize(PciRequestDto.Detokenize req);

    public PciResponseDto.CardDetailsResponse getCardDetails(PciRequestDto.cardDetails req);


}
