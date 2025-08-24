package com.example.ecommerce.exceptions;

import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends AppException {
    private static final String DEFAULT_MESSAGE = "Order not found";

    public OrderNotFoundException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.NOT_FOUND.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public OrderNotFoundException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.NOT_FOUND.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
