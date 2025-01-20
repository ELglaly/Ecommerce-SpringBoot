package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class DatabaseConnectionException extends AppException {
    public DatabaseConnectionException() {
        super("Database connection error");
        super.setErrorCode("DATABASE_CONNECTION_ERROR");
        super.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
    }
}
