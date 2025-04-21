package com.example.firmademo.services;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.firmademo.domain.Signatory;
import com.example.firmademo.dto.SignatoryDto;
import com.example.firmademo.exceptions.KeysNotFoundException;
import com.example.firmademo.repositories.SignatoryRepository;
import com.example.firmademo.security.CryptoService;

@Service
public class SignatoryServiceImpl implements SignatoryService {
    @Autowired
    SignatoryRepository signatoryRepository;
    @Autowired
    CryptoService cryptoService;

    public Signatory createSignatory(Signatory signatory) {
        return signatoryRepository.save(signatory);
    }

    public List<Signatory> getAllSignatories() {
        return signatoryRepository.findAll();
    }

    public Signatory getSignatoryById(Long id) {
        return signatoryRepository.findById(id)
                .orElseThrow(() -> new KeysNotFoundException(id)); // 
    }

    public Signatory getSignatoryByName(String signatoryName) {
        return signatoryRepository.findBySignatoryName(signatoryName).orElse(null);
    }

    public void deleteSignatoryById(Long id) {
        Signatory signatory = getSignatoryById(id);
        signatoryRepository.delete(signatory);
    }

    public void createSignatoryKeys(Signatory signatory) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA"); // NoSuchAlgorithmException
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            signatory.setSignatoryPublicKey(publicKeyBase64);
            signatory.setSignatoryPrivateKeyUnencrypted(privateKeyBase64);
            String encryptedPrivateKey = cryptoService.encryptAES(privateKeyBase64);
            signatory.setSignatoryPrivateKey(encryptedPrivateKey);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("RSA algorithm not found", e);
        }
    }

    public Signatory convertDtoToSignatory(SignatoryDto signatoryDto) {
        Signatory signatory = new Signatory();
        signatory.setSignatoryName(signatoryDto.getSignatoryName());
        signatory.setSignatoryPass(signatoryDto.getSignatoryPass());
        createSignatoryKeys(signatory);
        return createSignatory(signatory);
    }

    public Signatory createSignatoryFromDto(SignatoryDto signatoryDto) {
        Signatory signatory = convertDtoToSignatory(signatoryDto);
        return createSignatory(signatory);
    }
}