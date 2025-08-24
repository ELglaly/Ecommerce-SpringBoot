package com.example.ecommerce.exceptions.user;

import com.example.ecommerce.exceptions.AppException;
import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class RoleAlreadyExistsException extends AppException {

    private static final String DEFAULT_MESSAGE = "Role already exists";
    private static final String DEFAULT_CODE = "ROLE_ALREADY_EXISTS";

    public RoleAlreadyExistsException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.BAD_REQUEST.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public RoleAlreadyExistsException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.BAD_REQUEST.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

}
