package com.example.ecommerce.exceptions.user;

import com.example.ecommerce.exceptions.AppException;
import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedAccessException extends AppException {

    private static final String DEFAULT_MESSAGE = "Unauthorized access";

    public UnauthorizedAccessException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.UNAUTHORIZED.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public UnauthorizedAccessException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.UNAUTHORIZED.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
