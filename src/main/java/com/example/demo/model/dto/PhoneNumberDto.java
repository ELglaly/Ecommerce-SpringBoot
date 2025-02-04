package com.example.demo.model.dto;

import lombok.Data;

@Data
public class PhoneNumberDto {
    private Long id;
    private String countryCode;
    private String number;
}