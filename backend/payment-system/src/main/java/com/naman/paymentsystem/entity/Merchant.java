package com.naman.paymentsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naman.paymentsystem.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant extends BaseEntity {

    private String merchantCode;

    private String merchantName;

    private String apiKey;

    private String secretKey;

    private String email;

    private String website;

}