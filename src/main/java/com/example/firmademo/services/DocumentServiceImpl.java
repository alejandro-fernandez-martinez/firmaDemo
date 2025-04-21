package com.example.firmademo.services;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.firmademo.domain.Document;
import com.example.firmademo.dto.DocumentBase64Dto;
import com.example.firmademo.dto.DocumentDto;
import com.example.firmademo.repositories.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    public Document createDocument(Document document){
        return documentRepository.save(document);
    }

    public List<Document> getAllDocuments(){
        return documentRepository.findAll();
    }

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    public Document getDocumentByDocumentHash(String documentHash){
        return documentRepository.findByDocumentHash(documentHash).orElse(null);
    }

    public void deleteDocumentById(Long id){
        Document document = getDocumentById(id);
        documentRepository.delete(document);
    }

    public void createDocumentContent(MultipartFile file, Document document){
        try{
            byte[] fileBytes = file.getBytes(); //IOException
            String base64Content = Base64.getEncoder().encodeToString(fileBytes);
            document.setDocumentContent(base64Content);
        }catch (IOException e){
            throw new RuntimeException("Error to read file",e);
        }
    }

    public void createDocumentHash(MultipartFile file, Document document){
        try {
            byte[] fileBytes = file.getBytes(); //IOException
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); //NoSuchAlgorithmException
            byte[] hashBytes = digest.digest(fileBytes);
            String base64Hash = Base64.getEncoder().encodeToString(hashBytes);
            document.setDocumentHash(base64Hash);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }

    public void createDocumentHashFromBase64(String documentContent, Document document) {
        try {
            byte[] fileBytes = Base64.getDecoder().decode(documentContent); 
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); //NoSuchAlgorithmException
            byte[] hashBytes = digest.digest(fileBytes);
            String base64Hash = Base64.getEncoder().encodeToString(hashBytes);
            document.setDocumentHash(base64Hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm is not available", e);
        }
    }

    public String calculateHashFromFile(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes(); //IOException
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); //NoSuchAlgorithmException
            byte[] hashBytes = digest.digest(fileBytes);
            return Base64.getEncoder().encodeToString(hashBytes);
    
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculating hash from file", e);
        }
    }

    public Document convertDtoToDocument(DocumentDto documentDto){
        Document document = new Document();
        document.setDocumentName(documentDto.getDocumentName());
        createDocumentContent(documentDto.getFileAttach(), document);
        createDocumentHash(documentDto.getFileAttach(), document);
        return createDocument(document);
    }

    public Document convertBase64DtoToDocument(DocumentBase64Dto documentBase64Dto){
        Document document = new Document();
        document.setDocumentName(documentBase64Dto.getDocumentName());
        document.setDocumentContent(documentBase64Dto.getDocumentContent());
        createDocumentHashFromBase64(document.getDocumentContent(), document);
        return createDocument(document);
    }

}