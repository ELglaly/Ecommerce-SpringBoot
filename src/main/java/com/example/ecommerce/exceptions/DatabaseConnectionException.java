package com.example.ecommerce.exceptions;

import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class DatabaseConnectionException extends AppException {
    private static final String DEFAULT_MESSAGE = "Database connection error";

    public DatabaseConnectionException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public DatabaseConnectionException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
