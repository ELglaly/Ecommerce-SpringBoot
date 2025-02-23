package com.example.Ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class APIRequestException extends AppException {
    public APIRequestException(String endPoint) {
        super("API request error: "+endPoint);
        super.setStatus(HttpStatus.BAD_REQUEST);
        super.setErrorCode("API_REQUEST_ERROR");
    }
}
