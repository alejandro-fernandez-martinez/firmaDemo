package com.example.firmademo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignatoryDto {
    private String signatoryName;
    private String signatoryPass;
}