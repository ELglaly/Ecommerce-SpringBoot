package com.example.ecommerce.response;



public record ErrorResponse(String message, int statusCode, String timestamp, String path) {
}

