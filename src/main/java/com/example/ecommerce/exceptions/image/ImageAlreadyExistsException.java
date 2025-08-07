package com.example.ecommerce.exceptions.image;

import com.example.ecommerce.exceptions.AppException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
public class ImageAlreadyExistsException extends AppException {
    public ImageAlreadyExistsException(String message) {
        super(message);
        this.setErrorCode("IMAGE_ALREADY_EXISTS");
        this.setStatus(org.springframework.http.HttpStatus.CONFLICT);
    }
}
