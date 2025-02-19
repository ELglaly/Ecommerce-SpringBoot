package com.example.demo.model.dto;

import com.example.demo.model.dto.AddressDto;
import com.example.demo.model.dto.PhoneNumberDto;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.order.OrderDto;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    private Set<PhoneNumberDto> phoneNumbers;
    private AddressDto address;

}
