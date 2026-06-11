package com.naman.paymentsystem.repository;

import com.naman.paymentsystem.entity.CardToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardTokenRepository extends JpaRepository<CardToken, Long> {

    Optional<CardToken> findByToken(String token);


}
