package com.example.Ecommerce.exceptions.category;


import com.example.Ecommerce.exceptions.AppException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
public class CategoryAlreadyExistsException extends AppException {
    public CategoryAlreadyExistsException(String message) {
       super(message);
         this.setErrorCode("CATEGORY_ALREADY_EXISTS");
            this.setStatus(org.springframework.http.HttpStatus.CONFLICT);
    }

}
