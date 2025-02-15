package com.example.demo.exceptions.product;

import com.example.demo.exceptions.AppException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends AppException {
    public ProductNotFoundException(String message) {
        super(message);
        this.setErrorCode("PRODUCT_NOT_FOUND");
        this.setStatus(org.springframework.http.HttpStatus.NOT_FOUND);
    }
}
