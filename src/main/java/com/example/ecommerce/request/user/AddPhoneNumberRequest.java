package com.example.ecommerce.request.user;

import com.example.ecommerce.enums.PhoneNumberType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

public record AddPhoneNumberRequest(
        @NotBlank(message = "Country code is required")
        @Pattern(regexp = "\\+\\d{1,4}", message = "Invalid country code")
        String countryCode,

        @NotBlank(message = "Number is required")
        @Pattern(regexp = "\\d{7,15}", message = "Invalid phone number")
        String number,

        @NotNull(message = "Phone number type is required")
        PhoneNumberType phoneNumberType)
{
        // Static builder method to get a new builder instance
        public static Builder builder() {
                return new Builder();
        }

        public static class Builder {
                private String countryCode;
                private String number;
                private PhoneNumberType phoneNumberType;

                public Builder countryCode(String countryCode) {
                        this.countryCode = countryCode;
                        return this;
                }

                public Builder number(String number) {
                        this.number = number;
                        return this;
                }

                public Builder phoneNumberType(PhoneNumberType phoneNumberType) {
                        this.phoneNumberType = phoneNumberType;
                        return this;
                }

                // Allow setting type via String
                public Builder phoneNumberType(String phoneNumberType) {
                        this.phoneNumberType = PhoneNumberType.fromString(phoneNumberType);
                        return this;
                }

                public AddPhoneNumberRequest build() {
                        return new AddPhoneNumberRequest(
                                countryCode, number, phoneNumberType
                        );
                }
        }
}
