package com.example.Ecommerce.response;


import lombok.Data;

@Data

public class ApiResponse {
    private Object data;
    private String message;

    public ApiResponse(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
