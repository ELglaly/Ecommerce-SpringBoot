package com.example.ecommerce.request.user;

import lombok.Builder;


public record AddPhoneNumberRequest (
     String countryCode,
     String number)
{
}
