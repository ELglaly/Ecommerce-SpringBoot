package com.example.Ecommerce.exceptions.user;

import com.example.Ecommerce.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedAccessException extends AppException {
    public UnauthorizedAccessException(String message) {
        super("Unauthorized access : "+message);
        super.setStatus(HttpStatus.UNAUTHORIZED);
        super.setErrorCode("UNAUTHORIZED_ACCESS");
    }
}
