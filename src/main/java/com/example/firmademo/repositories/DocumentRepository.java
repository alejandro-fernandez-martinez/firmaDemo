package com.example.firmademo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.firmademo.domain.Document;

public interface DocumentRepository extends JpaRepository <Document,Long>{
    Optional<Document> findByDocumentHash (String documentHash);
}
