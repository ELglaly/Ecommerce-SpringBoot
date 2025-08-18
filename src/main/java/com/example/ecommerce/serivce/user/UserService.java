package com.example.ecommerce.serivce.user;

import com.example.ecommerce.exceptions.user.AccountIsNotActivatedException;
import com.example.ecommerce.exceptions.user.UserNotFoundException;
import com.example.ecommerce.exceptions.user.UserAlreadyExistsException;
import com.example.ecommerce.mapper.AddressMapper;
import com.example.ecommerce.mapper.PhoneNumberMapper;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.repository.user.UserRepository;
import com.example.ecommerce.request.user.CreateUserRequest;
import com.example.ecommerce.request.user.LoginRequest;
import com.example.ecommerce.request.user.UpdateUserRequest;
import com.example.ecommerce.security.jwt.JwtService;
import com.example.ecommerce.serivce.email.IRegistrationEmail;
import com.example.ecommerce.serivce.email.IResetPasswordEmail;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final IRegistrationEmail registrationEmail;
    private final IResetPasswordEmail resetPasswordEmail;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PhoneNumberMapper phoneNumberMapper;
    private final AddressMapper addressMapper;
    String rootPath = Paths.get("").toAbsolutePath().toString();

    public UserService(IRegistrationEmail registrationEmail, IResetPasswordEmail resetPasswordEmail, UserRepository userRepository, UserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService, PhoneNumberMapper phoneNumberMapper, AddressMapper addressMapper) {
        this.registrationEmail = registrationEmail;
        this.resetPasswordEmail = resetPasswordEmail;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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
    @CacheEvict(value = "userCache", allEntries = true)
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
    public String authenticate(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword())
        );
        if(authentication.isAuthenticated()) {
            User user = Optional.ofNullable(userRepository.findByUsernameOrUserContactEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail(),User.class ))
                    .orElseThrow(() -> new UserNotFoundException(rootPath,"User with username or email " + loginRequest.getUsernameOrEmail() + " not found"));
            if (user.getUserSecurity().isActivated()) {
                return JwtService.generateToken(user);
            } else {
                {
                    throw new AccountIsNotActivatedException(rootPath);
                }
            }
        } else {
            return "Login Failed";
        }
    }

    @Override
    public UserDTO createUser(CreateUserRequest request) {
        validateUserDoesNotExist(request.getEmail(), request.getUsername());
        User user = userMapper.toEntity(request);
        // Hash password
        user.getUserSecurity().setHashedPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);
        registrationEmail.sendSimpleMessage(user.getUserContact().getEmail(),user.getUsername());
        return userMapper.toDto(user);
    }

    private void validateUserDoesNotExist(String email, String username) {
        if (userRepository.existsByUserContactEmail(email)) {
            throw new UserAlreadyExistsException(rootPath,"User email " + email + " already exists");
        }
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(rootPath,"User with username " + username + " already exists");
        }
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



   /** * Deletes a user by their ID extracted from the JWT token.
    * This method checks if the user exists and then deletes them.
    * @param token The JWT token containing the user ID.
    * @throws UserNotFoundException if the user with the given ID does not exist.
    */
    @Override
    @Transactional
    public void deleteUser(String token) {
        Long id = jwtService.extractUserId(token);
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(rootPath,"User with ID " + id + " not found"));
        userRepository.deleteById(id);
    }

    /**
     * Activates a user by their username.
     * This method retrieves the user by username, sets their activation status to true, and saves the user.
     * @param username The username of the user to be activated.
     * @throws UserNotFoundException if the user with the given username does not exist.
     */
    @Transactional(
            label = "{activateUser, userService}",
            rollbackFor = {UserNotFoundException.class}
    )
    @Override
    public void activateUser(String username) {
        User user = Optional.ofNullable(userRepository.findByUsername(username, User.class))
                .orElseThrow(() -> new UserNotFoundException(rootPath,"User not found"));
        user.getUserSecurity().setActivated(true);
        userRepository.save(user);
    }

    /**
     * Changes the password of a user.
     * This method verifies the current password, checks if the new passwords match, and updates the user's password.
     * @param token The JWT token of the user.
     * @param password The current password of the user.
     * @param newPassword The new password to be set.
     * @param confirmPassword Confirmation of the new password.
     * @throws IllegalArgumentException if the new passwords do not match or if the current password is incorrect.
     * @throws UserNotFoundException if the user with the given token does not exist.
     */
    @Transactional(
            label = "{changePassword, userService}",
            rollbackFor = {UserNotFoundException.class, IllegalArgumentException.class}
    )
    @Override
    public void changePassword(String token, String password, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        String username = jwtService.extractUsername(token);
        User user = Optional.ofNullable(userRepository.findByUsername(username, User.class))
                .orElseThrow(() -> new UserNotFoundException(rootPath,"User not found"));
        if (passwordEncoder.matches(password, user.getUserSecurity().getHashedPassword())) {
            user.getUserSecurity().setHashedPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Password is incorrect");
        }
    }

    /**
     * Resets the password for a user identified by their email.
     * This method checks if the user exists and if their account is activated, then sends a reset password email.
     * @param email The email of the user whose password is to be reset.
     * @throws UserNotFoundException if the user with the given email does not exist.
     * @throws AccountIsNotActivatedException if the user's account is not activated.
     */
    @Transactional(
            label = "{resetPassword, userService}",
            rollbackFor = {UserNotFoundException.class, AccountIsNotActivatedException.class}
    )
    @Override
    public void resetPassword(String email) {
      User user = Optional.ofNullable( userRepository.findByUserContactEmail(email, User.class))
                .orElseThrow(() -> new UserNotFoundException(rootPath,"User with email " + email + " not found"));
      if (user.getUserSecurity().isActivated()) {
         resetPasswordEmail.sendEmail(user.getUserContact().getEmail(),user.getUsername());
      } else {
          throw new AccountIsNotActivatedException(rootPath);
      }
    }

    /**
     * Saves the new password after a reset.
     * This method currently does not implement any functionality.
     * @param password The new password to be saved.
     * @param confirmPassword Confirmation of the new password.
     */
    @Transactional(
            label = "{resetPasswordSaved, userService}",
            rollbackFor = {IllegalArgumentException.class}
    )
    @Override
    public void resetPasswordSaved(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        //TODO: Implement the logic to save the new password
        // This could involve updating the user's password in the database
        // For now, this method does not perform any action

    }

}