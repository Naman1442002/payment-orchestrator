package com.naman.paymentsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "card_token")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardToken extends BaseEntity {

    private String token;

    private String last4digitCardNumber;

    private String expiryMonth;

    private String expiryYear;

    private String cardHolderName;

    private String cardType;

    private String encryptedPan;

}