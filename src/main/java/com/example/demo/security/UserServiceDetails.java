package com.example.demo.security;

import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.serivce.user.IUserManagementService;
import com.example.demo.serivce.user.IUserService;
import com.example.demo.serivce.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
