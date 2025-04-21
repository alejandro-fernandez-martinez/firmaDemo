package com.example.firmademo.services;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.firmademo.domain.Document;
import com.example.firmademo.domain.Signatory;
import com.example.firmademo.domain.Signature;
import com.example.firmademo.dto.DocumentBase64Dto;
import com.example.firmademo.dto.DocumentDto;
import com.example.firmademo.dto.SignatureDto;
import com.example.firmademo.repositories.SignatureRepository;
import com.example.firmademo.security.CryptoService;

@Service
public class SignatureServiceImpl implements SignatureService {
    @Autowired
    SignatureRepository signatureRepository;
    @Autowired
    SignatoryService signatoryService;
    @Autowired
    CryptoService cryptoService;
    @Autowired
    DocumentService documentService;

    public Signature createSignature(Signature signature) {
        return signatureRepository.save(signature);
    }

    public Signature createSignatureWithIds(Long documentId, Long signatoryId) {
        try {
            Signatory signatory = signatoryService.getSignatoryById(signatoryId);
            String encryptedPrivateKey = signatory.getSignatoryPrivateKey();
            String privateKeyBase64 = cryptoService.decryptAES(encryptedPrivateKey);
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA"); 
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Document document = documentService.getDocumentById(documentId);
            String hashBase64 = document.getDocumentHash();
            byte[] hashBytes = Base64.getDecoder().decode(hashBase64);

            java.security.Signature signer = java.security.Signature.getInstance("SHA256withRSA");
            signer.initSign(privateKey);
            signer.update(hashBytes);
            byte[] signatureBytes = signer.sign();
            String signatureBase64 = Base64.getEncoder().encodeToString(signatureBytes);

            Signature signature = new Signature();
            signature.setSignature(signatureBase64);
            signature.setSignatureDate(LocalDateTime.now());
            signature.setSignatory(signatory);
            signature.setDocument(document);

            return createSignature(signature);

        } catch (Exception e) {
            throw new RuntimeException("Error creating signature", e);
        }
    }

    public Signature createSignatureFromDocumentDto(DocumentDto documentDto, Long signatoryId) {
        Document document = documentService.convertDtoToDocument(documentDto);
        return createSignatureWithIds(document.getDocumentId(), signatoryId);
    }

    public Signature createSignatureFromSignatureDto(SignatureDto signatureDto) {
        Document document = documentService.convertBase64DtoToDocument(
                new DocumentBase64Dto(signatureDto.getDocumentName(), signatureDto.getDocumentContent()));
        Signatory signatory = signatoryService.getSignatoryByName(signatureDto.getSignatoryName());
        return createSignatureWithIds(document.getDocumentId(), signatory.getSignatoryId());
    }

    public boolean verifySignature(MultipartFile file, Long signatoryId, Long signatureId) {
        try {
            byte[] fileBytes = file.getBytes();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(fileBytes);

            Signatory signatory = signatoryService.getSignatoryById(signatoryId);
            String publicKeyBase64 = signatory.getSignatoryPublicKey();
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            Signature signature = getSignatureById(signatureId);
            String signatureBase64 = signature.getSignature();
            byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);

            java.security.Signature verifier = java.security.Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(hashBytes);

            return verifier.verify(signatureBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error verifying signature", e);
        }
    }

    public boolean verifySignatureFromVerifySignatureDto(MultipartFile file, Long signatoryId, String signatureBase64) {
        try {
            byte[] fileBytes = file.getBytes();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(fileBytes);

            Signatory signatory = signatoryService.getSignatoryById(signatoryId);
            String publicKeyBase64 = signatory.getSignatoryPublicKey();
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);

            java.security.Signature verifier = java.security.Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(hashBytes);

            return verifier.verify(signatureBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error verifying signature", e);
        }
    }

    public List<Signature> getAllSignatures() {
        return signatureRepository.findAll();
    }

    public Signature getSignatureById(Long id) {
        return signatureRepository.findById(id).orElse(null);
    }

    public List<Signature> getSignaturesByDocument(Document document) {
        return signatureRepository.findByDocument(document);
    }

    public void deleteSignaturetById(Long id) {
        Signature signature = getSignatureById(id);
        signatureRepository.delete(signature);
    }
}