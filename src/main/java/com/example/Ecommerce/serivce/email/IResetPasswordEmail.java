package com.example.Ecommerce.serivce.email;

public interface IResetPasswordEmail {
    void sendEmail(String to, String username);
}
