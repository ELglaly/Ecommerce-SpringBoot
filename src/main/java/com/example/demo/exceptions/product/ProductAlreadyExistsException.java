package com.example.demo.exceptions.product;

import com.example.demo.exceptions.CustomRuntimeException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
public class ProductAlreadyExistsException extends CustomRuntimeException {
    public ProductAlreadyExistsException(String message) {
        super(message);
        this.setErrorCode("PRODUCT_ALREADY_EXISTS");
        this.setStatus(org.springframework.http.HttpStatus.CONFLICT);
    }
}
