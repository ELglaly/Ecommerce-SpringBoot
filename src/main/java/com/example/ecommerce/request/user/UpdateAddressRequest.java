package com.example.ecommerce.request.user;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.enums.PhoneNumberType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * UpdateAddressRequest is a DTO class record used to encapsulate the data required for updating a user's address.
 * It includes validation annotations to ensure that the input data meets specific criteria.
 */
public record UpdateAddressRequest (

    @NotBlank(message = "Street is required")
    @Size(min= 3, max = 100, message = "Street name must be between 3 - 100 character")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Street must contain only letters, numbers, and spaces")
    @Column(nullable = false, length = 100)
    String street,

    @NotBlank(message = "City is required")
    @Column(nullable = false, length = 50)
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "City must contain only letters and spaces")
    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters")
    String city,


    @NotBlank(message = "ZIP code is required")
    @Pattern(regexp = "\\d{5}(-\\d{4})?", message = "ZIP code must be in the format 12345 or 12345-6789")
    @Column(nullable = false, length = 10)
    String zip,

    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 50, message = "Country name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Country must contain only letters and spaces")
    @Column(nullable = false, length = 50)
    String country
) {

}
