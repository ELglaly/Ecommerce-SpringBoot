package com.example.ecommerce.security;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.repository.user.UserLoginProjection;
import com.example.ecommerce.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceDetails implements UserDetailsService {
    private final UserRepository userRepository;
    public UserServiceDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginProjection user = Optional.ofNullable(userRepository.findByUsername(username,UserLoginProjection.class))
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
        return MyUserDetails.buildUserDetails(user);
    }
}
