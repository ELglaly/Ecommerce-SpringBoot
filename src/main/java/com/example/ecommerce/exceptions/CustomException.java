package com.example.ecommerce.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@Setter
public class CustomException extends Exception {
    private String errorCode;
    private String message;
    private HttpStatus status;
    public CustomException(String message) {
        this.setMessage(message);
        this.setErrorCode("VALIDATION_ERROR");
        this.setStatus(BAD_REQUEST);
    }
}
