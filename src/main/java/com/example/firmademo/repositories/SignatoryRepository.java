package com.example.firmademo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.firmademo.domain.Signatory;

public interface SignatoryRepository extends JpaRepository <Signatory,Long>{
    Optional<Signatory> findBySignatoryName(String signatoryName);
}
