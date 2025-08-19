package com.example.ecommerce.serivce.user;

import com.example.ecommerce.exceptions.user.UserNotFoundException;
import com.example.ecommerce.exceptions.user.UserAlreadyExistsException;
import com.example.ecommerce.mapper.AddressMapper;
import com.example.ecommerce.mapper.PhoneNumberMapper;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.repository.user.UserRepository;
import com.example.ecommerce.request.user.UpdateUserRequest;
import com.example.ecommerce.security.jwt.JwtService;
import com.example.ecommerce.serivce.email.EmailService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PhoneNumberMapper phoneNumberMapper;
    private final AddressMapper addressMapper;
    String rootPath = Paths.get("").toAbsolutePath().toString();

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           PasswordEncoder passwordEncoder, JwtService jwtService, PhoneNumberMapper phoneNumberMapper, AddressMapper addressMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.phoneNumberMapper = phoneNumberMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    @Transactional(readOnly = true)
    @CacheEvict(value = "userCache", allEntries = true)
    public UserDTO getUserDtoById(Long id) {
        return userMapper.toDto(getUserById(id));
    }
    @Override
    @Transactional(readOnly = true)
    @CacheEvict(value = "userCache", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(rootPath,"User with ID " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByUserContactEmail(email,User.class))
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(rootPath,"User with email " + email + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByUsername(String username) {
        return  Optional.ofNullable(userRepository.findByUsername(username, User.class))
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(rootPath,"User with username " + username + " not found"));
    }


    @Override
    @Transactional(label = "{updateUser, userService}",
            rollbackFor = {UserNotFoundException.class}
    )
    public UserDTO updateUser(String token, UpdateUserRequest request) {

        Long id = jwtService.extractUserId(token);

        User existingUser = Optional.ofNullable(userRepository.findById(id,User.class))
                .orElseThrow(() -> new UserNotFoundException(rootPath,"User with ID " + id + " not found"));

        updateUserData(existingUser, request);
        return saveUpdatedUser(existingUser);
    }

 /**
  * Saves the updated user entity to the database and returns the UserDTO representation.
 * @param existingUser The user entity to be saved.
 * @return The UserDTO representation of the saved user.
 */
private UserDTO saveUpdatedUser(User existingUser) {
    User updatedUser = userRepository.save(existingUser);
    return userMapper.toDto(updatedUser);
}

/**
 * Updates the user data with the information from the UpdateUserRequest.
 * This method updates the user's first name, last name, birth date, addresses, and phone numbers.
 *
 * @param existingUser The user entity to be updated.
 * @param request      The request containing the new user data.
 */
private void updateUserData(User existingUser, UpdateUserRequest request) {
    existingUser.setFirstName(request.firstName());
    existingUser.setLastName(request.lastName());
    existingUser.setBirthDate(request.birthDate());
    existingUser.getUserContact().setAddress(request.addresses().stream()
            .map(addressMapper::toEntity)
                    .toList());
    existingUser.getUserContact().setPhoneNumber(request.phoneNumbers().stream()
            .map(phoneNumberMapper::toEntity)
            .collect(Collectors.toSet()));
}

}