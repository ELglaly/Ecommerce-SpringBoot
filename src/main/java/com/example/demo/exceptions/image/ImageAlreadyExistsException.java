package com.example.demo.exceptions.image;

import com.example.demo.exceptions.CustomRuntimeException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
public class ImageAlreadyExistsException extends CustomRuntimeException {
    public ImageAlreadyExistsException(String message) {
        super(message);
        this.setErrorCode("IMAGE_ALREADY_EXISTS");
        this.setStatus(org.springframework.http.HttpStatus.CONFLICT);
    }
}
