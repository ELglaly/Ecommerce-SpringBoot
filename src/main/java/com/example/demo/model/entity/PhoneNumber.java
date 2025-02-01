package com.example.demo.model.entity;

import com.example.demo.exceptions.ValidationException;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String countryCode;
    private String number;
    public PhoneNumber(String countryCode, String number) {
        setCountryCode(countryCode);
       setNumber(number);
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private boolean isValidCountryCode(String code) {
        return code.matches("\\+?\\d{1,4}"); // Allows "+" followed by 1-4 digits
    }

    private boolean isValidNumber(String num) {
        return num.matches("\\d{7,15}"); // Allows only 7-15 digits
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
         if (isValidCountryCode(countryCode)) {
             this.countryCode = countryCode;
         }
         else
             throw new ValidationException("Country code is invalid");
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
       if (isValidNumber(number)) {
           this.number = number;
       }
       else
           throw new ValidationException("Phone number is invalid");
    }
}
