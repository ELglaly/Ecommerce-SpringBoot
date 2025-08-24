package com.example.ecommerce.enums;

import com.example.ecommerce.exceptions.user.PhoneNumberException;
import org.checkerframework.checker.units.qual.Volume;

import java.util.Arrays;

public enum PhoneNumberType {
    MOBILE,
    HOME,
    WORK;

    public static PhoneNumberType fromString(String type) {
        return Arrays.stream(values())
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new PhoneNumberException("Invalid PhoneNumberType"));
    }

}
