package com.example.demo.exceptions;

public class ImageAlreadyExistsException extends RuntimeException {
    public ImageAlreadyExistsException(String message) {
        super(message);
    }
}
