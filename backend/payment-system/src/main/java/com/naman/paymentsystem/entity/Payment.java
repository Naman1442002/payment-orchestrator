package com.naman.paymentsystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naman.paymentsystem.enums.PaymentMethod;
import com.naman.paymentsystem.enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "payments", uniqueConstraints = {@UniqueConstraint(columnNames = {"merchant_order_id", "merchant_id"})})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment extends BaseEntity {


    private String merchantOrderId;

    private BigDecimal amount;

    private String currency;

    private String sessionId;

    @Enumerated(EnumType.STRING)
    private State paymentStatus;

    private String customerEmail;

    private String customerPhone;

    @ManyToOne
    private Merchant merchant;

}