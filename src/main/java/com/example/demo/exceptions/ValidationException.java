package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends AppException {
    public ValidationException(String message) {
        super("Validation error: "+message);
        setErrorCode("VALIDATION_ERROR");
        setStatus(HttpStatus.BAD_REQUEST);
    }
}
