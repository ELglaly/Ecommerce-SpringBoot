package com.example.demo.exceptions.user;

import com.example.demo.exceptions.AppException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends AppException {
    public RoleNotFoundException(String message) {
        super(message);
        this.setErrorCode("ROLE_NOT_FOUND");
        this.setStatus(org.springframework.http.HttpStatus.NOT_FOUND);
    }
}
