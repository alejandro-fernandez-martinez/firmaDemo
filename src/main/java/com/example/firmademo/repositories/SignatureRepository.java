package com.example.firmademo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.firmademo.domain.Document;
import com.example.firmademo.domain.Signature;

public interface SignatureRepository extends JpaRepository <Signature,Long>{
    List<Signature> findByDocument(Document document);
}
