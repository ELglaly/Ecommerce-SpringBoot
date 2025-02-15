package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends AppException {
    public OrderNotFoundException(String message) {
        super(message);
        this.setErrorCode("ORDER_NOT_FOUND");
        this.setStatus(org.springframework.http.HttpStatus.NOT_FOUND);
    }
}
