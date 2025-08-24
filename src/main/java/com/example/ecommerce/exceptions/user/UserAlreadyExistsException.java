package com.example.ecommerce.exceptions.user;

import com.example.ecommerce.exceptions.AppException;
import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends AppException {
    private static final String DEFAULT_MESSAGE = "User already exists with the provided details.";

    public UserAlreadyExistsException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.CONFLICT.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public UserAlreadyExistsException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.CONFLICT.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
