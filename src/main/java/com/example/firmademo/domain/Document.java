package com.example.firmademo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "documentId")

@Entity
public class Document {
    @Id
    @GeneratedValue
    private Long documentId;
    @Column(unique = true)
    private String documentName;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String documentContent;
    private String documentHash;
}