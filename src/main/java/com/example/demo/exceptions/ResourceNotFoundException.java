package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends AppException {

    public ResourceNotFoundException(String message, String resourceName) {
        super(message);
        super.setErrorCode(resourceName.toUpperCase()+"_ALREADY_EXISTS");
        super.setStatus(HttpStatus.NOT_FOUND);
    }
}
