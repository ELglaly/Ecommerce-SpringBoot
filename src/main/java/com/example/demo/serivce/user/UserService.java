package com.example.demo.serivce.user;

import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.user.CreateUserRequest;
import com.example.demo.request.user.UpdateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToUserDto)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found", "User"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .map(this::convertToUserDto)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found", "User"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        return  Optional.ofNullable(userRepository.findByUsername(username))
                .map(this::convertToUserDto)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found", "User"));
    }

    @Override
    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        validateUserDoesNotExist(request.getEmail(), request.getUsername(), null);
        User user = createUserFromRequest(request);
        user.setPassword(request.getPassword()); // Hash password
        user = userRepository.save(user);
        return convertToUserDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UpdateUserRequest request) {
        // id for because if the user need to change email -=> check it
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found", "User"));

        validateUserDoesNotExist(request.getEmail(), request.getUsername(), id);

        // Update only the fields provided in the request
        if (request.getEmail() != null) {
            existingUser.setEmail(request.getEmail());
        }
        if (request.getUsername() != null) {
            existingUser.setUsername(request.getUsername());
        }


        existingUser = userRepository.save(existingUser);
        return convertToUserDto(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found", "User"));

        userRepository.deleteById(id);
    }

    // Helper Methods

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User createUserFromRequest(CreateUserRequest request) {
        return modelMapper.map(request, User.class);
    }

    private void validateUserDoesNotExist(String email, String username, Long excludeUserId) {
        if (email != null && userRepository.existsByEmailAndIdNot(email, excludeUserId)) {
            throw new ResourceAlreadyExistsException("User with email " + email + " already exists", "User");
        }
        if (username != null && userRepository.existsByUsernameAndIdNot(username, excludeUserId)) {
            throw new ResourceAlreadyExistsException("User with username " + username + " already exists", "User");
        }
    }
}