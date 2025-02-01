package com.example.demo.request.user;

import com.example.demo.model.entity.Address;
import com.example.demo.model.entity.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
public class UpdateUserRequest {

    @NotNull
    @NotBlank(message= "Must Be a valid username")
    private String username;
    @Email(message = "Must be a valid Email")
    private String email;

    @Valid
    @NotNull
    private List<PhoneNumber> phoneNumber;

    @Valid
    @NotNull
    private Address address;


    public @NotNull @NotBlank(message = "Must Be a valid username") String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @NotBlank(message = "Must Be a valid username") String username) {
        this.username = username;
    }

    public @Email(message = "Must be a valid Email") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Must be a valid Email") String email) {
        this.email = email;
    }

    public @Valid @NotNull List<PhoneNumber> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Valid @NotNull List<PhoneNumber> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @Valid @NotNull Address getAddress() {
        return address;
    }

    public void setAddress(@Valid @NotNull Address address) {
        this.address = address;
    }
}
