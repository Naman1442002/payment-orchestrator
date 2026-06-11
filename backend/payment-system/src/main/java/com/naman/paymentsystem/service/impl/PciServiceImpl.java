package com.naman.paymentsystem.service.impl;

import com.naman.paymentsystem.dto.PciRequestDto;
import com.naman.paymentsystem.dto.PciResponseDto;
import com.naman.paymentsystem.entity.CardToken;
import com.naman.paymentsystem.repository.CardTokenRepository;
import com.naman.paymentsystem.service.EncryptionService;
import com.naman.paymentsystem.service.PciService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class PciServiceImpl implements PciService {

    @Autowired
    CardTokenRepository cardTokenRepository;

    @Autowired
    EncryptionService encryptionService;


    @Override
    public PciResponseDto.Tokenize tokenize(PciRequestDto.Tokenize req) {
//        1. validation of req
        validatePciRequest(req);

//        2. Select Card Type
        String cardType = detectCardType(req.getPanNumber());

//        3. Generate Card Token
        String token = generateToken();

        CardToken cardToken = buildCardToken(
                req,
                token,
                cardType

        );


        CardToken savedCardToken = cardTokenRepository.save(cardToken);
        return new PciResponseDto.Tokenize(savedCardToken.getToken());
    }

    @Override
    public PciResponseDto.Detokenize deTokenize(PciRequestDto.Detokenize req) {
//       1. find card details using token passed
        Optional<CardToken> cardToken = cardTokenRepository.findByToken(req.getToken());
        CardToken tokenEntity = cardToken.orElseThrow(() -> new RuntimeException("CardTOken Not Find "));
//       2. decrypt pan
        String decrytpPan = encryptionService.decrypt(tokenEntity.getEncryptedPan());

//       3. return pan
        return PciResponseDto.Detokenize.builder()
                .cardHolderName(tokenEntity.getCardHolderName())
                .expiryMonth(Integer.valueOf(tokenEntity.getExpiryMonth()))
                .expiryYear(Integer.valueOf(tokenEntity.getExpiryYear()))
                .panNumber(decrytpPan)
                .build();
    }

    @Override
    public PciResponseDto.CardDetailsResponse getCardDetails(PciRequestDto.cardDetails req) {

//        1. get cardTokenEntity By token
        Optional<CardToken> cardToken = cardTokenRepository.findByToken(req.getToken());
        CardToken tokenEntity = cardToken.orElseThrow(() -> new RuntimeException("CardTOken Not Find "));

//        2. map to cardDetailResponse
        //        3. return
        return PciResponseDto.CardDetailsResponse.builder()
                .cardType(tokenEntity.getCardType())
                .token(req.getToken())
                .last4(tokenEntity.getLast4digitCardNumber())
                .cardHolderName(tokenEntity.getCardHolderName())
                .expiryYear(Integer.valueOf(tokenEntity.getExpiryYear()))
                .expiryMonth(Integer.valueOf(tokenEntity.getExpiryMonth()))
                .build();

    }

    private CardToken buildCardToken(PciRequestDto.Tokenize req, String token, String cardType) {

        CardToken cardToken = new CardToken();

        cardToken.setToken(token);
        cardToken.setCardType(cardType);
        cardToken.setLast4digitCardNumber(
                req.getPanNumber().substring(
                        req.getPanNumber().length() - 4));
        cardToken.setExpiryMonth(String.valueOf(req.getExpiryMonth()));
        cardToken.setExpiryYear(String.valueOf(req.getExpiryYear()));
        cardToken.setCardHolderName(req.getCardHolderName());
        cardToken.setEncryptedPan(
                encryptionService.encrypt(req.getPanNumber())
        );


        return cardTokenRepository.save(cardToken);
    }

    private String generateToken() {
        return "tok_" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "");
    }

    private String detectCardType(String cardNumber) {

        if (cardNumber.startsWith("4")) {
            return "VISA";
        }

        if (cardNumber.startsWith("5")) {
            return "MASTERCARD";
        }

        if (cardNumber.startsWith("37")) {
            return "AMEX";
        }

        if (cardNumber.startsWith("60")) {
            return "RUPAY";
        }

        return "UNKNOWN";
    }

    private void validatePciRequest(
            PciRequestDto.Tokenize request) {

        if (request.getPanNumber() == null ||
                request.getPanNumber().isBlank()) {
            throw new RuntimeException("Card number required");
        }

        if (request.getExpiryMonth() == null ||
                request.getExpiryMonth() < 1 || request.getExpiryMonth() > 12) {
            throw new RuntimeException(" Valid Expiry month required");
        }

        if (request.getExpiryYear() == null ||
                request.getExpiryYear() < LocalDate.now().getYear()) {
            throw new RuntimeException(" Valid Expiry year required");
        }

        if (request.getCardHolderName() == null ||
                request.getCardHolderName().isBlank()) {
            throw new RuntimeException("Card holder name required");
        }
    }


}
