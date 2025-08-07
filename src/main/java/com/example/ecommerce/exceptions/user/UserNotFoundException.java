package com.example.ecommerce.exceptions.user;

import com.example.ecommerce.exceptions.AppException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class UserNotFoundException extends AppException {
    public UserNotFoundException(String message) {
        super(message);
        this.setErrorCode("USER_NOT_FOUND");
        this.setStatus(org.springframework.http.HttpStatus.NOT_FOUND);
    }
}
