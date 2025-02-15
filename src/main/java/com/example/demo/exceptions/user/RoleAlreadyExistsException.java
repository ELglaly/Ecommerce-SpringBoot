package com.example.demo.exceptions.user;

import com.example.demo.exceptions.CustomRuntimeException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class RoleAlreadyExistsException extends CustomRuntimeException {
    public RoleAlreadyExistsException(String message) {
        super(message);
        this.setErrorCode("ROLE_ALREADY_EXISTS");
        this.setStatus(org.springframework.http.HttpStatus.BAD_REQUEST);
    }

}
