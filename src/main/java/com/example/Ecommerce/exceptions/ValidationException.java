package com.example.Ecommerce.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends AppException {
    public ValidationException(String message) {
        super(message);
        this.setErrorCode("VALIDATION_ERROR");
        this.setStatus(HttpStatus.BAD_REQUEST);
    }
}
