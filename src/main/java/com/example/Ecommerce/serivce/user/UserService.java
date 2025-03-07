package com.example.Ecommerce.serivce.user;

import com.example.Ecommerce.exceptions.user.UserNotFoundException;
import com.example.Ecommerce.exceptions.user.UserAlreadyExistsException;
import com.example.Ecommerce.mapper.IUserMapper;
import com.example.Ecommerce.mapper.UserMapper;
import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.model.entity.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.request.user.CreateUserRequest;
import com.example.Ecommerce.request.user.LoginRequest;
import com.example.Ecommerce.request.user.UpdateUserRequest;
import com.example.Ecommerce.security.jwt.JwtService;
import com.example.Ecommerce.serivce.email.EmailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class UserService implements IUserService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final IUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(EmailService emailService, UserRepository userRepository, IUserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserDtoById(Long id) {
        return userMapper.toDto(getUserById(id));
    }
    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        return  Optional.ofNullable(userRepository.findByUsername(username))
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword())
        );
        if(authentication.isAuthenticated()) {
            return JwtService.generateToken(loginRequest.getUsernameOrEmail());
        } else {
            return "Login Failed";
        }
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        validateUserDoesNotExist(request.getEmail(), request.getUsername());
        User user = userMapper.toEntityFromAddRequest(request);
        // Hash password
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);
        emailService.sendSimpleMessage(request.getEmail());
        return userMapper.toDto(user);
    }

    private void validateUserDoesNotExist(String email, String username) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User email " + email + " already exists");
        }
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with username " + username + " already exists");
        }
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UpdateUserRequest request) {
        // id for because if the user need to change email -=> check it
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isEmpty())
        {
            throw new  UserNotFoundException("User with ID " + id + " not found");
        }
        validateUserDoesNotExist(request.getEmail(), request.getUsername(),existingUser.get().getEmail(),existingUser.get().getUsername());
        User updatedUser = userMapper.toEntityFromUpdateRequest(request);
        updatedUser.setId(id);
        updatedUser=userRepository.save(updatedUser);
        return userMapper.toDto(updatedUser);
    }

    private void validateUserDoesNotExist(String email,  String username, String email1,String username1) {
        if (userRepository.existsByEmail(email) && !email.equals(email1)) {
            throw new UserAlreadyExistsException("User  email " + email + " already exists");
        }
        if (userRepository.existsByUsername(username) && !username.equals(username1)) {
            throw new UserAlreadyExistsException("User with username " + username + " already exists");
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        userRepository.deleteById(id);
    }

}