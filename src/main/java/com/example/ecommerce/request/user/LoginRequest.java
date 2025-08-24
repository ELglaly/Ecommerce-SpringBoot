package com.example.ecommerce.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


public record LoginRequest (
    @NotBlank(message = "Username or email is required")
    @Size(min = 3, max = 50, message = "Username or email must be 3-50 characters")
     String usernameOrEmail,
    @NotBlank(message = "Password is required")
     String password)
{
}
