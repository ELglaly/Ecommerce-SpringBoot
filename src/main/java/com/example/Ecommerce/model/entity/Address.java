package com.example.Ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "Street is mandatory")
    @Column(nullable = false, length = 100)
    private String street;

    @NotBlank(message = "City is mandatory")
    @Column(nullable = false, length = 50)
    private String city;

    @NotBlank(message = "State is mandatory")
    @Column(nullable = false, length = 50)
    private String state;

    @NotBlank(message = "ZIP code is mandatory")
    @Pattern(regexp = "\\d{5}(-\\d{4})?", message = "ZIP code must be valid")
    @Column(nullable = false, length = 10)
    private String zip;

    @NotBlank(message = "Country is mandatory")
    @Column(nullable = false, length = 50)
    private String country;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
