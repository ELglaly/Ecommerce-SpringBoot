package com.example.Ecommerce.exceptions.image;

import com.example.Ecommerce.exceptions.AppException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends AppException {
    public ImageNotFoundException(String message) {
        super(message);
        this.setErrorCode("IMAGE_NOT_FOUND");
        this.setStatus(org.springframework.http.HttpStatus.NOT_FOUND);
    }
}
