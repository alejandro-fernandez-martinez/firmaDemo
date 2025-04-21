package com.example.firmademo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignatureDto {
    private String signatoryName;
    private String documentName;
    private String documentContent;
}