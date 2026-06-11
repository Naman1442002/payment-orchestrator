package com.naman.paymentsystem.entity;

import com.naman.paymentsystem.enums.OrderState;
import com.naman.paymentsystem.enums.PaymentMethod;
import com.naman.paymentsystem.enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transactions")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {
    private String gatewayTransactionId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private State paymentStatus;

    private String bankReferenceNumber;

    private String failureReason;

    @ManyToOne
    private Payment payment;


}