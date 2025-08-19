package com.example.ecommerce.exceptions;

import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailException extends AppException {

    private static final String DEFAULT_MESSAGE = "An error occurred while processing the email.";
    public EmailException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.BAD_REQUEST.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public EmailException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.BAD_REQUEST.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
