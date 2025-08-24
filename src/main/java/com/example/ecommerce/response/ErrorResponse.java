package com.example.ecommerce.response;


import lombok.Builder;

@Builder
public record ErrorResponse(
        String message,
        int statusCode,
        String timestamp,
        String path) {
}

