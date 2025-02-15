package com.example.demo.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomRuntimeException extends RuntimeException{
    private HttpStatus status;
    private String errorCode;
    public CustomRuntimeException(String message) {
        super(message);
    }
}


