package com.example.Ecommerce.model.dto;

import com.example.Ecommerce.model.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private User user;
}
