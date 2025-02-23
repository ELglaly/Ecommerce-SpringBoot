package com.example.Ecommerce.exceptions.category;

import com.example.Ecommerce.exceptions.AppException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends AppException {
    public CategoryNotFoundException(String message) {
        super(message);
        this.setErrorCode("CATEGORY_NOT_FOUND");
        this.setStatus(org.springframework.http.HttpStatus.NOT_FOUND);
    }
}
