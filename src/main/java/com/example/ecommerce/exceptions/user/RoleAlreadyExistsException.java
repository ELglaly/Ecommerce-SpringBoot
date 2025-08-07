package com.example.ecommerce.exceptions.user;

import com.example.ecommerce.exceptions.AppException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class RoleAlreadyExistsException extends AppException {
    public RoleAlreadyExistsException(String message) {
        super(message);
        this.setErrorCode("ROLE_ALREADY_EXISTS");
        this.setStatus(org.springframework.http.HttpStatus.BAD_REQUEST);
    }

}
