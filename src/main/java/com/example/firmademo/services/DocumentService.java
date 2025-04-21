package com.example.firmademo.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.firmademo.domain.Document;
import com.example.firmademo.dto.DocumentBase64Dto;
import com.example.firmademo.dto.DocumentDto;

public interface DocumentService {

    Document createDocument(Document document);
    List<Document> getAllDocuments();
    Document getDocumentById(Long id);
    Document getDocumentByDocumentHash(String documentHash);
    void deleteDocumentById(Long id);
    void createDocumentContent(MultipartFile file, Document document);
    void createDocumentHash(MultipartFile file, Document document);
    void createDocumentHashFromBase64(String documentContent, Document document);
    String calculateHashFromFile(MultipartFile file);
    Document convertDtoToDocument(DocumentDto documentDto);
    Document convertBase64DtoToDocument(DocumentBase64Dto documentBase64Dto);
}