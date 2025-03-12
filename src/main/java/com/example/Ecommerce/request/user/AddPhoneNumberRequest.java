package com.example.Ecommerce.request.user;

import lombok.Builder;

@Builder
public class AddPhoneNumberRequest {
    private String countryCode;
    private String number;
}
