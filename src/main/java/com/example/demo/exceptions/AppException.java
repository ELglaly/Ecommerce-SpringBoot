package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    private String errorCode;
    private HttpStatus status;
    public AppException(String message) {
        super(message);
    }
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public HttpStatus getStatus() {
        return status;
    }
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
