package com.example.demo.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

          @ExceptionHandler(AppException.class)
          public ResponseEntity<Map<String, Object>> handleGlobalException(AppException ex) {
              Map<String, Object> errorDetails = new HashMap<>();
              errorDetails.put("message", ex.getMessage());
              errorDetails.put("errorCode",ex.getErrorCode());
              errorDetails.put("timestamp", LocalDateTime.now());
              return new ResponseEntity<>(errorDetails,ex.getStatus());
          }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
