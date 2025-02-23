package com.example.Ecommerce.exceptions;

public class InvalidFieldException extends AppException {

    public InvalidFieldException(String message) {
        super("Invalid field: "+message);
    }
}
