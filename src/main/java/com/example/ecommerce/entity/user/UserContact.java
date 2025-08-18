package com.example.ecommerce.entity.user;

import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@Embeddable
public class UserContact {

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is Required")
    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PhoneNumber> phoneNumber = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> address =new ArrayList<>();

}
