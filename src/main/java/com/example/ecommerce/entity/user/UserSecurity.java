package com.example.ecommerce.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import static com.example.ecommerce.constants.ErrorMessages.UserError.PASSWORD_EMPTY;
import static com.example.ecommerce.constants.ErrorMessages.UserError.PASSWORD_PATTERN;

@Getter
@Setter
@Embeddable
public class UserSecurity {

    @NotBlank(message = PASSWORD_EMPTY)
    @Column(nullable = false)
    @Pattern(regexp = "^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[@#$%^&+=!])(?!.*\\s).{8,20}$",
            message = PASSWORD_PATTERN)
    private String hashedPassword;

    @Column(nullable = false)
    private boolean isActivated = false;

    @Column(nullable = false)
    private boolean isAccountNonExpired=true; // Account expiration status

    @Column(nullable = false)
    private boolean isAccountNonLocked=true; // Account lock status

    @Column(nullable = false)
    private boolean isCredentialsNonExpired=true; // Credentials expiration status
}
