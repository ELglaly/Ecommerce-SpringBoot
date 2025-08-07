package com.example.ecommerce.exceptions.category;

import com.example.ecommerce.exceptions.AppException;
import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends AppException {

    private static final String DEFAULT_MESSAGE = "Category not found";

    public CategoryNotFoundException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.NOT_FOUND.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public CategoryNotFoundException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.NOT_FOUND.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
