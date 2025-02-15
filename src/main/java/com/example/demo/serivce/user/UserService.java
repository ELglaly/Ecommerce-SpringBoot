package com.example.demo.serivce.user;

import com.example.demo.exceptions.user.UserNotFoundException;
import com.example.demo.exceptions.user.UserAlreadyExistsException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.user.CreateUserRequest;
import com.example.demo.request.user.UpdateUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@EnableTransactionManagement
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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
    public UserDto createUser(CreateUserRequest request) {
        validateUserDoesNotExist(request.getEmail(), request.getUsername());
        User user = userMapper.toEntityFromAddRequest(request);
        System.out.println("user = " + user.toString());
        // Hash password
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    private void validateUserDoesNotExist( String email, String username) {
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
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        validateUserDoesNotExist(request.getEmail(), request.getUsername(),existingUser.getEmail(),existingUser.getUsername());
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