package com.example.ecommerce.exceptions.image;

import com.example.ecommerce.exceptions.AppException;
import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
public class ImageAlreadyExistsException extends AppException {

    //Default message then the two constructors
    private static final String DEFAULT_MESSAGE = "Image already exists for this product";

    public ImageAlreadyExistsException(String path) {
        super(new ErrorResponse(
                DEFAULT_MESSAGE,
                HttpStatus.CONFLICT.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }

    public ImageAlreadyExistsException(String path, String customMessage) {
        super(new ErrorResponse(
                customMessage,
                HttpStatus.CONFLICT.value(),
                String.valueOf(System.currentTimeMillis()),
                path
        ));
    }
}
