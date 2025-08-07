package com.example.ecommerce.exceptions.category;


import com.example.ecommerce.exceptions.AppException;
import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
public class CategoryAlreadyExistsException extends AppException {

    private static final String DEFAULT_MESSAGE = "Category already exists";

    public CategoryAlreadyExistsException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.CONFLICT.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public CategoryAlreadyExistsException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.CONFLICT.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
