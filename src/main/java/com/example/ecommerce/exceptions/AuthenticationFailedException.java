package com.example.ecommerce.exceptions;

import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationFailedException extends AppException {
    private static final String DEFAULT_MESSAGE = "Authentication failed";

    public AuthenticationFailedException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.UNAUTHORIZED.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public AuthenticationFailedException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.UNAUTHORIZED.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
