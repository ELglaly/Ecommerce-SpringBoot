package com.example.Ecommerce.request.user;

import com.example.Ecommerce.model.entity.Address;
import com.example.Ecommerce.model.entity.PhoneNumber;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Builder
@Data
public class UpdateUserRequest {


    @NotBlank(message = "Last Name cannot be blank")

    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotNull
    @NotBlank(message= "Must Be a valid username")
    private String username;

    @Email(message = "Must be a valid Email")
    @NotNull(message = "Email cannot be null")
    private String email;


    private List<UpdatePhoneNumberRequest> phoneNumber;

    private UpdateAddressRequest address;

}
