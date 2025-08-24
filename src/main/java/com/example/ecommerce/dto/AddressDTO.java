package com.example.ecommerce.dto;

import jakarta.validation.constraints.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.ecommerce.entity.Address}
 */
public record AddressDTO(@Size(message = "Street name must be between 3 - 100 character", min = 3, max = 100) @Pattern(message = "Street must contain only letters, numbers, and spaces", regexp = "^[a-zA-Z0-9\\s]+$") @NotBlank(message = "Street is required") String street, @Size(message = "City name must be between 2 and 50 characters", min = 2, max = 50) @Pattern(message = "City must contain only letters and spaces", regexp = "^[a-zA-Z\\s]+$") @NotBlank(message = "City is required") String city, @Pattern(message = "ZIP code must be in the format 12345 or 12345-6789", regexp = "\\d{5}(-\\d{4})?") @NotBlank(message = "ZIP code is required") String zip, @NotNull @Size(message = "Country name must be between 2 and 50 characters", min = 2, max = 50) @Pattern(message = "Country must contain only letters and spaces", regexp = "^[a-zA-Z\\s]+$") @NotEmpty @NotBlank(message = "Country is required") String country) implements Serializable {
  }