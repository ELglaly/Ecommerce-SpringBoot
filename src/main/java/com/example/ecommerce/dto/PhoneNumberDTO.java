package com.example.ecommerce.dto;

import com.example.ecommerce.enums.PhoneNumberType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

/**
 * DTO for {@link com.example.ecommerce.entity.PhoneNumber}
 */
public record PhoneNumberDTO(@Pattern(message = "Invalid country code", regexp = "\\+?\\d{1,4}") @NotBlank(message = "Country code is required") String countryCode, @Pattern(message = "Invalid phone number", regexp = "\\d{7,15}") @NotBlank(message = "Number is required") String number, @NotNull(message = "Phone number type is required") PhoneNumberType phoneNumberType) implements Serializable {
  }