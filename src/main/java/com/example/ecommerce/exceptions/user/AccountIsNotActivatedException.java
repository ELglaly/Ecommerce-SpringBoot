package com.example.ecommerce.exceptions.user;

import com.example.ecommerce.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
public class AccountIsNotActivatedException extends AppException {
    public AccountIsNotActivatedException()
    {
        super("Account is not activated");
        this.setErrorCode("ACCOUNT_NOT_ACTIVATED");
        this.setStatus(HttpStatus.BAD_REQUEST);
    }
}
