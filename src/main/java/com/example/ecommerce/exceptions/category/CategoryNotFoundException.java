package com.example.ecommerce.exceptions.category;

import com.example.ecommerce.exceptions.AppException;
import com.example.ecommerce.response.ErrorResponse;
import com.example.ecommerce.util.CategoryUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends AppException {



    public CategoryNotFoundException(String path) {
        super(new ErrorResponse(
                CategoryUtils.CATEGORY_NOT_FOUND,
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
