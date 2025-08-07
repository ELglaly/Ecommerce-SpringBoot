package com.example.ecommerce.entity;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.enums.PhoneNumberType;
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
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Country code is required")
    @Pattern(regexp = "\\+?\\d{1,4}", message = "Invalid country code")
    @Column(nullable = false)
    private String countryCode;

    @NotBlank(message = "Number is required")
    @Pattern(regexp = "\\d{7,15}", message = "Invalid phone number")
    @Column(nullable = false)
    private String number;

    @NotNull(message = "Phone number type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhoneNumberType phoneNumberType;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
