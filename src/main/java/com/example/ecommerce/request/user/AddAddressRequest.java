package com.example.ecommerce.request.user;

import com.example.ecommerce.entity.user.User;
import lombok.Builder;

@Builder
public class AddAddressRequest {
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private User user;
}
