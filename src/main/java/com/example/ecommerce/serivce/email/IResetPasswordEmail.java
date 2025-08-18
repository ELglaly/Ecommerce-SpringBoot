package com.example.ecommerce.serivce.email;

public interface IResetPasswordEmail {
    void sendEmail(String to, String username);
}
