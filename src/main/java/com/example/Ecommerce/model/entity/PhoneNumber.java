package com.example.Ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Country code is mandatory")
    @Pattern(regexp = "\\+?\\d{1,4}", message = "Invalid country code")
    @Column(nullable = false)
    private String countryCode;

    @NotBlank(message = "Number is mandatory")
    @Pattern(regexp = "\\d{7,15}", message = "Invalid phone number")
    @Column(nullable = false)
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
