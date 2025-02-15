package com.example.demo.exceptions.image;

import com.example.demo.exceptions.CustomRuntimeException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends CustomRuntimeException {
    public ImageNotFoundException(String message) {
        super(message);
        this.setErrorCode("IMAGE_NOT_FOUND");
        this.setStatus(org.springframework.http.HttpStatus.NOT_FOUND);
    }
}
