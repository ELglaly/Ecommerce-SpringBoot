package com.example.ecommerce.repository.user;


import com.example.ecommerce.entity.Role;
import com.example.ecommerce.entity.user.UserSecurity;
import lombok.Value;

import java.util.Collection;
import java.util.HashSet;

@Value
public class UserLoginProjection{
        String username;
        String email;
        String hashedPassword;
        Collection<Role> roles = new HashSet<>();
        boolean isActivated;
        boolean isAccountNonExpired;
        boolean isAccountNonLocked;
        boolean isCredentialsNonExpired;
}
