package com.example.firmademo.exceptions;

public class KeysNotFoundException extends RuntimeException {
    public KeysNotFoundException(Long id) {
        super("Cannot find keys: " + id);
    }
}
