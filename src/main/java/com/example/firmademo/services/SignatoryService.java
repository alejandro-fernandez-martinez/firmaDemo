package com.example.firmademo.services;

import java.util.List;

import com.example.firmademo.domain.Signatory;
import com.example.firmademo.dto.SignatoryDto;

public interface SignatoryService {

    Signatory createSignatory(Signatory signatory);
    List<Signatory> getAllSignatories();
    Signatory getSignatoryById(Long id);
    Signatory getSignatoryByName(String signatoryName);
    void deleteSignatoryById(Long id);
    void createSignatoryKeys(Signatory signatory);
    Signatory convertDtoToSignatory(SignatoryDto signatoryDto);
    Signatory createSignatoryFromDto(SignatoryDto signatoryDto);

}