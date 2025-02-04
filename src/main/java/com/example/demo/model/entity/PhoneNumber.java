package com.example.demo.model.entity;

import com.example.demo.exceptions.ValidationException;
import jakarta.persistence.*;
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
    private String countryCode;
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isValidCountryCode(String code) {
        return code.matches("\\+?\\d{1,4}"); // Allows "+" followed by 1-4 digits
    }

    private boolean isValidNumber(String num) {
        return num.matches("\\d{7,15}"); // Allows only 7-15 digits
    }


}
