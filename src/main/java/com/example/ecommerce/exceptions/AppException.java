package com.example.ecommerce.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AppException extends RuntimeException{
    private HttpStatus status;
    private String errorCode;
    public AppException(String message) {
        super(message);
    }
}


