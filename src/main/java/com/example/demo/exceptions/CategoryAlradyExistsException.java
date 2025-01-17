package com.example.demo.exceptions;

public class CategoryAlradyExistsException extends RuntimeException{
    public CategoryAlradyExistsException(String message) {
        super(message);
    }

}
