package com.example.firmademo.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CryptoService {

    @Value("${crypto.secret-key}")
    private String secretKey;

    public String encryptAES(String plainText) {
        try {
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting with AES", e);
        }
    }

    public String decryptAES(String encryptedText) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting with AES", e);
        }
    }
    
}
