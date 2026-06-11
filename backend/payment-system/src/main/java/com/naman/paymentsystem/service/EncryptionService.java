package com.naman.paymentsystem.service;

public interface EncryptionService {

    String encrypt(String plainText);

    String decrypt(String encryptedText);
}