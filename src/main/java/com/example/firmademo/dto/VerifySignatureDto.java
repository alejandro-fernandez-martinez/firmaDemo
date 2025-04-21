package com.example.firmademo.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifySignatureDto {
    private MultipartFile fileToVerify;
    private Long signatoryId;
    private String signatureBase64;
}