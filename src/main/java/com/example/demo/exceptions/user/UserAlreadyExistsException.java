package com.example.demo.exceptions.user;

import com.example.demo.exceptions.CustomRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends CustomRuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
        this.setErrorCode("USER_ALREADY_EXISTS");
        this.setStatus(HttpStatus.CONFLICT);
    }

}
