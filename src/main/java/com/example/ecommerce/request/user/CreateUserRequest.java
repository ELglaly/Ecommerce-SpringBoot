package com.example.ecommerce.request.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;

import static com.example.ecommerce.constants.ErrorMessages.UserError.*;

public record CreateUserRequest (

        @NotBlank(message = USERNAME_EMPTY)
        @Size(min = 3, max = 50, message = USERNAME_SIZE)
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = USERNAME_PATTERN)
        String username,

        @NotBlank(message = PASSWORD_EMPTY)
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$",
                message = PASSWORD_PATTERN)
        String password,

     @Email(message = EMAIL_INVALID)
      @NotBlank(message = EMAIL_EMPTY)
     String email,

     Set<@Valid AddPhoneNumberRequest> phoneNumber,
     List<@Valid AddAddressRequest> address
){


}
