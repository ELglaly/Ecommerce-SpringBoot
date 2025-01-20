package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends AppException{
    public ResourceAlreadyExistsException(String message, String resourceName) {
        super(message);
        super.setErrorCode(resourceName.toUpperCase()+ "_ALREADY_EXISTS");
        super.setStatus(HttpStatus.CONFLICT);
    }

}
