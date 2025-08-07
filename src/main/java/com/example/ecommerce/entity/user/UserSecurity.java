package com.example.ecommerce.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class UserSecurity {

    @NotBlank(message = "Password is Required")
    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character.")
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
