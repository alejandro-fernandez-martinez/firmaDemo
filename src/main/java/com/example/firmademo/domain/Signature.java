package com.example.firmademo.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "signatureId")

@Entity
public class Signature {
    @Id
    @GeneratedValue
    private Long signatureId;
    @Column(columnDefinition = "TEXT")
    private String signature;
    private LocalDateTime signatureDate;

    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Signatory signatory;

    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Document document;
}