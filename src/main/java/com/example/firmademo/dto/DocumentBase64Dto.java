package com.example.firmademo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentBase64Dto {
    private String documentName;
    private String documentContent;
}