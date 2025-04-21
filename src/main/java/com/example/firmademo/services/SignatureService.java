package com.example.firmademo.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.firmademo.domain.Document;
import com.example.firmademo.domain.Signature;
import com.example.firmademo.dto.DocumentDto;
import com.example.firmademo.dto.SignatureDto;

public interface SignatureService {

    Signature createSignature(Signature signature);
    Signature createSignatureWithIds(Long documentId, Long signatoryId);
    Signature createSignatureFromDocumentDto(DocumentDto documentDto, Long signatoryId);    
    Signature createSignatureFromSignatureDto(SignatureDto signatureDto);   
    boolean verifySignature(MultipartFile file, Long signatoryId, Long signatureId);
    boolean verifySignatureFromVerifySignatureDto(MultipartFile file, Long signatoryId, String signatureBase64);
    List<Signature> getAllSignatures();
    Signature getSignatureById(Long id);
    List<Signature> getSignaturesByDocument(Document document);
    void deleteSignaturetById(Long id);
}