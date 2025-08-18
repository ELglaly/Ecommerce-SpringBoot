package com.example.ecommerce.serivce.email;

public interface IRegistrationEmail {
    void sendSimpleMessage(String to, String username);
}