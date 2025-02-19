package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhoneNumberDto {
    private Long id;
    private String countryCode;
    private String number;
}