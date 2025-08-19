package com.example.ecommerce.request.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

import static com.example.ecommerce.constants.ErrorMessages.UserError.PASSWORD_PATTERN;
import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

public record CreateUserRequest (

    @NotNull
    @NotBlank(message= "Must Be valid username")
     String username,

    @NotNull(message = "Must be a valid password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$",
            message = PASSWORD_PATTERN)
     String password,
    @Email(message = "Must be a valid Email")
    @NotNull(message = "Must be a valid Email")
     String email,


     Set<@Valid AddPhoneNumberRequest> phoneNumber,
     List<@Valid AddAddressRequest> address
){


}
