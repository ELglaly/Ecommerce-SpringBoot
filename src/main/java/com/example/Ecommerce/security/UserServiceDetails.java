package com.example.Ecommerce.security;

import com.example.Ecommerce.model.entity.User;
import com.example.Ecommerce.repository.UserRepository;
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
         User user = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
        return MyUserDetails.buildUserDetails(user);
    }
}
