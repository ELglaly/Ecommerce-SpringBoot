package com.example.ecommerce.serivce.user;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.exceptions.user.AccountIsNotActivatedException;
import com.example.ecommerce.exceptions.user.UserAlreadyExistsException;
import com.example.ecommerce.exceptions.user.UserNotFoundException;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.repository.user.UserRepository;
import com.example.ecommerce.request.user.CreateUserRequest;
import com.example.ecommerce.request.user.LoginRequest;
import com.example.ecommerce.security.jwt.JwtService;
import com.example.ecommerce.serivce.email.EmailService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final String rootPath = Objects.requireNonNull(AuthServiceImpl.class.getResource("")).getPath();

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, EmailService emailService, PasswordEncoder passwordEncoder, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
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
            emailService.sendPasswordResetEmail(user.getUserContact().getEmail(),user.getUsername());
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
    public void resetPasswordSaved(String email,String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        //TODO: Implement the logic to save the new password
        // This could involve updating the user's password in the database
        // For now, this method does not perform any action

    }

    @Override
    public String login(LoginRequest loginRequest) {

      authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.usernameOrEmail(), loginRequest.password()));

        User user = Optional.ofNullable(userRepository.findByUsernameOrUserContactEmail(loginRequest.usernameOrEmail(), loginRequest.usernameOrEmail(),User.class ))
                    .orElseThrow(() -> new UserNotFoundException(rootPath,"User with username or email " + loginRequest.usernameOrEmail() + " not found"));

        return JwtService.generateToken(user);
    }

    @Override
    public void logout(String token) {
        // Validate token first

        String username = jwtService.extractUsername(token);
        if (username == null) {
            throw new UserNotFoundException(rootPath, "User not found");
        }

        // Add token to blacklist

        // Clear current security context (optional, for current request)
        SecurityContextHolder.clearContext();

    }

    @Override
    public UserDTO registerUser(CreateUserRequest request) {
        validateUserDoesNotExist(request.email(), request.username());
        User user = userMapper.toEntity(request);
        // Hash password
        user.getUserSecurity().setHashedPassword(passwordEncoder.encode(request.password()));
        user = userRepository.save(user);
        emailService.sendRegistrationEmail(user.getUserContact().getEmail(),user.getUsername());
        return userMapper.toDto(user);
    }

    private void validateUserDoesNotExist(String email, String username) {
        if (userRepository.existsByUserContactEmail(email)) {
            throw new UserAlreadyExistsException(rootPath, "User with email " + email + " already exists");
        }
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(rootPath, "User with username " + username + " already exists");
        }
    }
}
