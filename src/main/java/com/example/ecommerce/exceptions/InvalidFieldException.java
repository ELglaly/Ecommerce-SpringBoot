package com.example.ecommerce.exceptions;

public class InvalidFieldException extends AppException {

    public InvalidFieldException(String message) {
        super("Invalid field: "+message);
    }
}
