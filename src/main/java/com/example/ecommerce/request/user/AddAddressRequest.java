package com.example.ecommerce.request.user;

import com.example.ecommerce.entity.user.User;
import lombok.Builder;

public record AddAddressRequest (
     String street,
     String city,
     String state,
     String zip,
     String country)
{
}
