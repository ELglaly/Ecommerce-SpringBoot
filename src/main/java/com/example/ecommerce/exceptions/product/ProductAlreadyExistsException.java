package com.example.ecommerce.exceptions.product;

import com.example.ecommerce.exceptions.AppException;
import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
public class ProductAlreadyExistsException extends AppException {

    private static final String DEFAULT_MESSAGE = "Product already exists";

    public ProductAlreadyExistsException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.CONFLICT.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public ProductAlreadyExistsException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.CONFLICT.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
