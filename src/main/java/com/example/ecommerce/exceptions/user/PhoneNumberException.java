package com.example.ecommerce.exceptions.user;

import com.example.ecommerce.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhoneNumberException extends AppException {
    public PhoneNumberException(String message) {
        super(message);
    }

}
