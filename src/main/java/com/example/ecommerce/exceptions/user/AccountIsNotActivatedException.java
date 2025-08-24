package com.example.ecommerce.exceptions.user;

import com.example.ecommerce.exceptions.AppException;
import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountIsNotActivatedException extends AppException {

    private static final String DEFAULT_MESSAGE = "Account is not activated";

    public AccountIsNotActivatedException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.BAD_REQUEST.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public AccountIsNotActivatedException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.BAD_REQUEST.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
