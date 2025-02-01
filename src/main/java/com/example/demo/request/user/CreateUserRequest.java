package com.example.demo.request.user;

import com.example.demo.model.entity.Address;
import com.example.demo.model.entity.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

git public class CreateUserRequest {

    @NotNull
    @NotBlank(message= "Must Be valid username")
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$",
            message = "Password must be at least 6 characters long and at least one " +
                    "lowercase letter and at least one uppercase letter and contain at least one digit " +
                    "and at least one special character (@#$%^&+=!).")
    private String password;
    @Email(message = "Must be a valid Email")
    private String email;


    @Valid
    @NotNull
    private List<PhoneNumber> phoneNumber;

    @Valid
    @NotNull
    private Address address;


    public @NotNull @NotBlank(message = "Must Be valid username") String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @NotBlank(message = "Must Be valid username") String username) {
        this.username = username;
    }

    public @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$",
            message = "Password must be at least 6 characters long and at least one " +
                    "lowercase letter and at least one uppercase letter and contain at least one digit " +
                    "and at least one special character (@#$%^&+=!).") String getPassword() {
        return password;
    }

    public void setPassword(@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$",
            message = "Password must be at least 6 characters long and at least one " +
                    "lowercase letter and at least one uppercase letter and contain at least one digit " +
                    "and at least one special character (@#$%^&+=!).") String password) {
        this.password = password;
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
