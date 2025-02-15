package com.example.demo.exceptions.category;

import com.example.demo.exceptions.CustomRuntimeException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends CustomRuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
        this.setErrorCode("CATEGORY_NOT_FOUND");
        this.setStatus(org.springframework.http.HttpStatus.NOT_FOUND);
    }
}
