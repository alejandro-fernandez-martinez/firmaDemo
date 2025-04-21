package com.example.firmademo.exceptions;

public class SignatureVerificationFailed extends RuntimeException {
    public SignatureVerificationFailed(Long id) {
        super("Signature verification failed: " + id);
    }
}
