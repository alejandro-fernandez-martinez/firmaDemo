package com.example.firmademo.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.firmademo.domain.Signatory;
import com.example.firmademo.domain.Signature;
import com.example.firmademo.dto.DocumentDto;
import com.example.firmademo.dto.SignatoryDto;
import com.example.firmademo.dto.SignatureDto;
import com.example.firmademo.services.DocumentService;
import com.example.firmademo.services.SignatoryService;
import com.example.firmademo.services.SignatureService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class MainRestController {

    @Autowired
    public SignatoryService signatoryService;
    @Autowired
    public DocumentService documentService;
    @Autowired
    public SignatureService signatureService;

    @PostMapping("/createSignatory")
    public ResponseEntity<?> newElement(@Valid 
            @RequestBody SignatoryDto signatoryDto) {
        Signatory signatory = signatoryService.createSignatoryFromDto(signatoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(signatory.getSignatoryPublicKey());
    }

    @PostMapping("/createSignatureWithDocument") // form-data para enviar file
    public ResponseEntity<?> createSignatureWithDocument(@Valid 
            @ModelAttribute DocumentDto documentDto,
            @RequestParam("signatoryId") Long signatoryId) {
        Signature signature = signatureService.createSignatureFromDocumentDto(documentDto, signatoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(signature);
    }

    @PostMapping("/createSignatureWithBase64Document")
    public ResponseEntity<?> createSignatureWithBase64Document(@Valid 
            @RequestBody SignatureDto signatureDto) {
        Signature signature = signatureService.createSignatureFromSignatureDto(signatureDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(signature.getSignature());
    }

    @PostMapping("/verifySignatureWithDocument") // form-data para enviar file
    public ResponseEntity<?> createSignatureWithBase64Document(@Valid 
            @RequestParam("fileToVerify") MultipartFile fileToVerify,
            @RequestParam("signatoryId") Long signatoryId, 
            @RequestParam("signatureBase64") String signatureBase64) {
        Boolean isValid = signatureService.verifySignatureFromVerifySignatureDto(fileToVerify, signatoryId,
                signatureBase64);
        if (isValid) {
            return ResponseEntity.ok("Valid signature");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The signed document does not match the original. Invalid signature");
        }
    }
}
