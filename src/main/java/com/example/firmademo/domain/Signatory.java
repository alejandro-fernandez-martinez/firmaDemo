package com.example.firmademo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "signatoryId")

@Entity
public class Signatory {
    @Id
    @GeneratedValue
    private Long signatoryId;
    @Column(unique = true)
    private String signatoryName;
    private String signatoryPass;
    @Column(columnDefinition = "TEXT")
    private String signatoryPublicKey;
    @Column(columnDefinition = "TEXT")
    private String signatoryPrivateKey;
    @Column(columnDefinition = "TEXT")
    private String signatoryPrivateKeyUnencrypted;

}