package com.example.Ecommerce.request.user;

import com.example.Ecommerce.model.entity.User;
import lombok.Builder;

@Builder
public class UpdateAddressRequest {

    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private User user;
}
