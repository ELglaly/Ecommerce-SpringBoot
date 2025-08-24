package com.example.ecommerce.exceptions.user;

import com.example.ecommerce.exceptions.AppException;
import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * Exception thrown when a user is not found in the system.
 * This exception should be used when a user lookup fails to find a matching user.
 */
@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class UserNotFoundException extends AppException {
    private static final String DEFAULT_MESSAGE = "User not found";

    public UserNotFoundException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.NOT_FOUND.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public UserNotFoundException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.NOT_FOUND.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

}
