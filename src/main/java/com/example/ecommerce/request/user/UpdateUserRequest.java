package com.example.ecommerce.request.user;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.stream.XMLInputFactory;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * UpdateUserRequest is a record class that represents the request body for updating user information.
 * It includes fields for first name, last name, email, date of birth, addresses, and phone numbers.
 * Each field has validation constraints to ensure the data is valid before processing.
 */

@Builder
public record UpdateUserRequest (

        Long id,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
        String lastName,

        @Past(message = "Birth date must be in the past")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        Date birthDate,

        @NotNull
        List<@Valid UpdateAddressRequest> addresses,
        @NotNull
        @Size(min = 1, message = "At least one phone number is required")
        Set<@Valid UpdatePhoneNumberRequest> phoneNumbers
)
{
}
