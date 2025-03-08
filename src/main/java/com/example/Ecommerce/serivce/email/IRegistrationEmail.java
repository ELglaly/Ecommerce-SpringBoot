package com.example.Ecommerce.serivce.email;

public interface IRegistrationEmail {
    void sendSimpleMessage(String to, String username);
}