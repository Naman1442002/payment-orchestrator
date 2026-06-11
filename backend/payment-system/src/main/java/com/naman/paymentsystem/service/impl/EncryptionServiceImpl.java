package com.naman.paymentsystem.service.impl;

import com.naman.paymentsystem.service.EncryptionService;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private static final String ALGORITHM = "AES";

    private static final String SECRET_KEY =
            "MySecretKey12345"; // exactly 16 chars for AES-128

    private final SecretKeySpec secretKeySpec =
            new SecretKeySpec(
                    SECRET_KEY.getBytes(StandardCharsets.UTF_8),
                    ALGORITHM);

    @Override
    public String encrypt(String plainText) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encrypted =
                    cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    @Override
    public String decrypt(String encryptedText) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] decrypted =
                    cipher.doFinal(Base64.getDecoder().decode(encryptedText));

            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}