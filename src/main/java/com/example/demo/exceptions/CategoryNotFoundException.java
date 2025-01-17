package com.example.demo.exceptions;

import java.io.Serializable;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
