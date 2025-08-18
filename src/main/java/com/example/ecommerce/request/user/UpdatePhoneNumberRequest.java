package com.example.ecommerce.request.user;

import com.example.ecommerce.enums.PhoneNumberType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

/**
 * UpdatePhoneNumberRequest is a DTO class record used to encapsulate the data required for updating a user's phone number.
 * It includes validation annotations to ensure that the input data meets specific criteria.
 */

public record UpdatePhoneNumberRequest (
    @NotBlank(message = "Country code is required")
    @Pattern(regexp = "\\+?\\d{1,4}", message = "Invalid country code")
    @Column(nullable = false)
     String countryCode,

    @NotBlank(message = "Number is required")
    @Pattern(regexp = "\\d{7,15}", message = "Invalid phone number")
    @Column(nullable = false)
     String number,

    @NotNull(message = "Phone number type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
     PhoneNumberType phoneNumberType)
{
}
