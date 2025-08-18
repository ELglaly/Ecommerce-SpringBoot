package com.example.ecommerce.request.user;

import lombok.Builder;

@Builder
public class AddPhoneNumberRequest {
    private String countryCode;
    private String number;
}
