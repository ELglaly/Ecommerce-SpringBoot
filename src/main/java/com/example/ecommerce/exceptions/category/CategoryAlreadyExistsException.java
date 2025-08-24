package com.example.ecommerce.exceptions.category;


import com.example.ecommerce.exceptions.AppException;
import com.example.ecommerce.response.ErrorResponse;
import com.example.ecommerce.util.CategoryUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
public class CategoryAlreadyExistsException extends AppException {


    public CategoryAlreadyExistsException() {
        super(new ErrorResponse(
                CategoryUtils.CATEGORY_ALREADY_EXISTS,
                HttpStatus.CONFLICT.value(),
                String.valueOf(System.currentTimeMillis()),
                "/category"
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
