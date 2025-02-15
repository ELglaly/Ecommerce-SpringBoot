package com.example.demo.request.user;

import com.example.demo.model.entity.Address;
import com.example.demo.model.entity.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CreateUserRequest {

    @NotNull
    @NotBlank(message= "Must Be valid username")
    private String username;

    @NotNull(message = "Must be a valid password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$",
            message = "Password must be at least 6 characters long and at least one " +
                    "lowercase letter and at least one uppercase letter and contain at least one digit " +
                    "and at least one special character (@#$%^&+=!).")
    private String password;
    @Email(message = "Must be a valid Email")
    @NotNull(message = "Must be a valid Email")
    private String email;


    private List<AddPhoneNumberRequest> phoneNumber;
    private AddAddressRequest address;

}
