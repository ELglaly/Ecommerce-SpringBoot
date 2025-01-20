package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationFailedException extends AppException {
    public AuthenticationFailedException(String message) {
        super("Authentication failed : " + message);
        super.setStatus(HttpStatus.UNAUTHORIZED);
        super.setErrorCode("AUTHENTICATION_FAILED");
    }
}
