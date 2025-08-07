package com.example.ecommerce.entity;

import com.example.ecommerce.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Street is required")

    @Size(min= 3, max = 100, message = "Street name must be between 3 - 100 character")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Street must contain only letters, numbers, and spaces")
    @Column(nullable = false, length = 100)
    private String street;

    @NotBlank(message = "City is required")
    @Column(nullable = false, length = 50)
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "City must contain only letters and spaces")
    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters")
    private String city;


    @NotBlank(message = "ZIP code is required")
    @Pattern(regexp = "\\d{5}(-\\d{4})?", message = "ZIP code must be in the format 12345 or 12345-6789")
    @Column(nullable = false, length = 10)
    private String zip;

    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 50, message = "Country name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Country must contain only letters and spaces")
    @Column(nullable = false, length = 50)
    private String country;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
