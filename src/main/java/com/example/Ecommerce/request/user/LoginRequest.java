package com.example.Ecommerce.request.user;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
