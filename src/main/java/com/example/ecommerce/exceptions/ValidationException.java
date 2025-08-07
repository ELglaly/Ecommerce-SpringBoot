package com.example.ecommerce.exceptions;


import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends AppException {

    private static final String DEFAULT_MESSAGE = "Validation failed";

    public ValidationException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.BAD_REQUEST.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public ValidationException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.BAD_REQUEST.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
