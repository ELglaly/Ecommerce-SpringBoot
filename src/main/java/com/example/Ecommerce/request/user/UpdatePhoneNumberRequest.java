package com.example.Ecommerce.request.user;

import lombok.Builder;

@Builder
public class UpdatePhoneNumberRequest {
    private String countryCode;
    private String number;
}
