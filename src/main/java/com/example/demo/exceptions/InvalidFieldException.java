package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidFieldException extends AppException {

    public InvalidFieldException(String message) {
        super("Invalid field: "+message);
        super.setErrorCode("INVALID_FIELD");
        super.setStatus(HttpStatus.BAD_REQUEST);
    }
}
