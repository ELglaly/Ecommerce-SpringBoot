package com.example.ecommerce.exceptions;


import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(AppException ex) {
        if(ex.getErrorResponse()==null)
        {
             ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
            return ResponseEntity.status(errorResponse.statusCode()).body(errorResponse);
        }
        return ResponseEntity.status(ex.getErrorResponse().statusCode())
                .body(ex.getErrorResponse());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String message = errors.values().iterator().next();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentExceptions(IllegalArgumentException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
