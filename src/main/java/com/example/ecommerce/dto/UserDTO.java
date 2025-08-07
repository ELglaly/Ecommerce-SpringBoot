package com.example.ecommerce.dto;

import com.example.ecommerce.enums.UserRole;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.example.ecommerce.entity.user.User}
 */
public record UserDTO(@Size(message = "First name must be between 2 and 50 characters", min = 2, max = 50) @Pattern(message = "First name must contain only letters", regexp = "^[a-zA-Z]+$") @NotBlank(message = "Last name is required") String firstName, @Size(message = "Last name must be between 2 and 50 characters", min = 2, max = 50) @Pattern(message = "Last name must contain only letters", regexp = "^[a-zA-Z]+$") @NotBlank(message = "Last name is required") String lastName, @Size(message = "Username must be between 3 and 50 characters", min = 3, max = 50) @Pattern(message = "Username can only contain letters, numbers, dots, underscores, and hyphens", regexp = "^[a-zA-Z0-9._-]+$") @NotBlank(message = "Username is required") String username, Collection<RoleDto> roles, UserContactDto userContact, UserSecurityDto userSecurity) implements Serializable {
    /**
     * DTO for {@link com.example.ecommerce.entity.Role}
     */
    public record RoleDto(@NotNull(message = "User role is mandatory") UserRole userRole) implements Serializable {
    }

    /**
     * DTO for {@link com.example.ecommerce.entity.user.UserContact}
     */
    public record UserContactDto(@Email(message = "Email should be valid") @NotBlank(message = "Email is Required") String email, Set<PhoneNumberDTO> phoneNumber, List<AddressDTO> address) implements Serializable {
    }

    /**
     * DTO for {@link com.example.ecommerce.entity.user.UserSecurity}
     */
    public record UserSecurityDto(@Pattern(message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character.", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$") @NotBlank(message = "Password is Required") String hashedPassword) implements Serializable {
    }
}