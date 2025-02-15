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


    private UserDto(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthDate = builder.birthDate;
        this.username = builder.username;
        this.email = builder.email;
        this.phoneNumbers = builder.phoneNumbers;
        this.address = builder.address;
    }
    public static class Builder{
        private Long id;
        private String firstName;
        private String lastName;
        private Date birthDate;
        private String username;
        private String email;
        private Set<PhoneNumberDto> phoneNumbers;
        private AddressDto address;
        private Set<OrderDto> orders;
        private CartDto cart;
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder phoneNumbers(Set<PhoneNumberDto> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }
        public Builder address(AddressDto address) {
            this.address = address;
            return this;
        }
        public Builder orders(Set<OrderDto> orders) {
            this.orders = orders;
            return this;
        }
        public Builder cart(CartDto cart) {
            this.cart = cart;
            return this;
        }
        public UserDto build() {
            return new UserDto(this);
        }
    }


}
