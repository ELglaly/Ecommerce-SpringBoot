package com.example.demo.exceptions;

import java.io.Serializable;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
