package com.example.ecommerce.exceptions;

import com.example.ecommerce.response.ErrorResponse;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
public class AppException extends RuntimeException implements Serializable {

    private ErrorResponse errorResponse;
    private static final long serialVersionUID = 1L;

    public AppException(String message) {
        super(message);
    }

    public AppException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}


