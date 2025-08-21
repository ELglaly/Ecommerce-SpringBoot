package com.example.ecommerce.serivce.user;

import com.example.ecommerce.entity.user.UserContact;
import com.example.ecommerce.entity.user.UserSecurity;
import com.example.ecommerce.exceptions.user.AccountIsNotActivatedException;
import com.example.ecommerce.exceptions.user.UserAlreadyExistsException;
import com.example.ecommerce.exceptions.user.UserNotFoundException;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.repository.user.UserRepository;
import com.example.ecommerce.request.user.AddAddressRequest;
import com.example.ecommerce.request.user.AddPhoneNumberRequest;
import com.example.ecommerce.request.user.CreateUserRequest;
import com.example.ecommerce.request.user.UpdateUserRequest;
import com.example.ecommerce.security.jwt.JwtService;
import com.example.ecommerce.serivce.email.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserManagementServiceTest {

    @Mock
    private EmailService emailService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private AuthService authService;

    private User user;
    private UserDTO userDTO;
    private CreateUserRequest createUserRequest;
    private UpdateUserRequest updateUserRequest;
    private UserContact userContact;
    private UserSecurity userSecurity;

    @BeforeEach
    void setUp() {
        userSecurity = new UserSecurity();
        userSecurity.setHashedPassword("hashedPassword");
        userSecurity.setActivated(true);

        userContact = new UserContact();
        userContact.setEmail("test@example.com");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUserSecurity(userSecurity);
        user.setUserContact(userContact);

        userDTO = new UserDTO("Test", "User", "testuser", Collections.emptyList(), null, null);

        createUserRequest = new CreateUserRequest(
                "Test",
                "User",
                "testuser",Set.of(new AddPhoneNumberRequest("1234567890", "true")), List.of(
                new AddAddressRequest("0987654321", "Home", "123 Main St", "City", "State")
        )
        );

        updateUserRequest = UpdateUserRequest.builder()
                .firstName("Updated")
                .lastName("User")
                .birthDate(new Date())
                .addresses(Collections.emptyList())
                .phoneNumbers(Set.of())
                .build();
    }

    @Test
    void createUser_ReturnsUserDTO_WhenValidRequest() {
        when(userRepository.existsByUserContactEmail(createUserRequest.email())).thenReturn(false);
        when(userRepository.existsByUsername(createUserRequest.username())).thenReturn(false);
        when(userMapper.toEntity(createUserRequest)).thenReturn(user);
        when(passwordEncoder.encode(createUserRequest.password())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTO);

        UserDTO result = authService.registerUser(createUserRequest);

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userRepository).existsByUserContactEmail(createUserRequest.email());
        verify(userRepository).existsByUsername(createUserRequest.username());
        verify(userMapper).toEntity(createUserRequest);
        verify(passwordEncoder).encode(createUserRequest.password());
        verify(userRepository).save(user);
        verify(emailService).sendRegistrationEmail(anyString(), anyString());
    }

    @Test
    void createUser_ThrowsUserAlreadyExistsException_WhenEmailExists() {
        when(userRepository.existsByUserContactEmail(createUserRequest.email())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> authService.registerUser(createUserRequest));
        verify(userRepository).existsByUserContactEmail(createUserRequest.email());
    }

    @Test
    void createUser_ThrowsUserAlreadyExistsException_WhenUsernameExists() {
        when(userRepository.existsByUserContactEmail(createUserRequest.email())).thenReturn(false);
        when(userRepository.existsByUsername(createUserRequest.username())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> authService.registerUser(createUserRequest));
        verify(userRepository).existsByUserContactEmail(createUserRequest.email());
        verify(userRepository).existsByUsername(createUserRequest.username());
    }

    @Test
    void updateUser_ReturnsUserDTO_WhenValidRequest() {
        when(jwtService.extractUserId("token")).thenReturn(1L);
        when(userRepository.findById(1L, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTO);

        UserDTO result = userService.updateUser("token", updateUserRequest);

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(jwtService).extractUserId("token");
        verify(userRepository).findById(1L, User.class);
        verify(userRepository).save(user);
        verify(userMapper).toDto(user);
    }

    @Test
    void updateUser_ThrowsUserNotFoundException_WhenUserNotExists() {
        when(jwtService.extractUserId("token")).thenReturn(1L);
        when(userRepository.findById(1L, User.class)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.updateUser("token", updateUserRequest));
        verify(jwtService).extractUserId("token");
        verify(userRepository).findById(1L, User.class);
    }

    @Test
    void deleteUser_DeletesUser_WhenUserExists() {
        when(jwtService.extractUserId("token")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        authService.deleteUser("token");

        verify(jwtService).extractUserId("token");
        verify(userRepository).findById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_ThrowsUserNotFoundException_WhenUserNotExists() {
        when(jwtService.extractUserId("token")).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.deleteUser("token"));
        verify(jwtService).extractUserId("token");
        verify(userRepository).findById(1L);
        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    void activateUser_ActivatesUser_WhenUserExists() {
        userSecurity.setActivated(false);
        when(userRepository.findByUsername("testuser", User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        authService.activateUser("testuser");

        assertTrue(user.getUserSecurity().isActivated());
        verify(userRepository).findByUsername("testuser", User.class);
        verify(userRepository).save(user);
    }

    @Test
    void activateUser_ThrowsUserNotFoundException_WhenUserNotExists() {
        when(userRepository.findByUsername("testuser", User.class)).thenReturn(null);

        assertThrows(UserNotFoundException.class,
                () -> authService.activateUser("testuser"));
        verify(userRepository).findByUsername("testuser", User.class);
    }

    @Test
    void changePassword_ChangesPassword_WhenValidRequest() {
        when(jwtService.extractUsername("token")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser", User.class)).thenReturn(user);
        when(passwordEncoder.matches("oldPassword", "hashedPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("newHashedPassword");
        when(userRepository.save(user)).thenReturn(user);

        authService.changePassword("token", "oldPassword", "newPassword", "newPassword");

        verify(jwtService).extractUsername("token");
        verify(userRepository).findByUsername("testuser", User.class);
        verify(passwordEncoder).matches("oldPassword", "hashedPassword");
        verify(passwordEncoder).encode("newPassword");
        verify(userRepository).save(user);
    }

    @Test
    void changePassword_ThrowsIllegalArgumentException_WhenPasswordsDoNotMatch() {
        assertThrows(IllegalArgumentException.class,
                () -> authService.changePassword("token", "oldPassword", "newPassword", "differentPassword"));
    }

    @Test
    void changePassword_ThrowsIllegalArgumentException_WhenCurrentPasswordIncorrect() {
        when(jwtService.extractUsername("token")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser", User.class)).thenReturn(user);
        when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> authService.changePassword("token", "wrongPassword", "newPassword", "newPassword"));
        verify(passwordEncoder).matches("wrongPassword", "hashedPassword");
    }

    @Test
    void changePassword_ThrowsUserNotFoundException_WhenUserNotExists() {
        when(jwtService.extractUsername("token")).thenReturn("testuser");
        when(userRepository.findByUsername("testuser", User.class)).thenReturn(null);

        assertThrows(UserNotFoundException.class,
                () -> authService.changePassword("token", "oldPassword", "newPassword", "newPassword"));
        verify(jwtService).extractUsername("token");
        verify(userRepository).findByUsername("testuser", User.class);
    }

    @Test
    void resetPassword_SendsEmail_WhenUserExistsAndActivated() {
        when(userRepository.findByUserContactEmail("test@example.com", User.class)).thenReturn(user);

        authService.resetPassword("test@example.com");

        verify(userRepository).findByUserContactEmail("test@example.com", User.class);
        verify(emailService).sendPasswordResetEmail("test@example.com", "testuser");
    }

    @Test
    void resetPassword_ThrowsAccountIsNotActivatedException_WhenAccountNotActivated() {
        userSecurity.setActivated(false);
        when(userRepository.findByUserContactEmail("test@example.com", User.class)).thenReturn(user);

        assertThrows(AccountIsNotActivatedException.class, () -> authService.resetPassword("test@example.com"));
        verify(userRepository).findByUserContactEmail("test@example.com", User.class);
        verify(emailService, never()).sendPasswordResetEmail(anyString(), anyString());
    }

    @Test
    void resetPassword_ThrowsUserNotFoundException_WhenUserNotExists() {
        when(userRepository.findByUserContactEmail("test@example.com", User.class)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> authService.resetPassword("test@example.com"));
        verify(userRepository).findByUserContactEmail("test@example.com", User.class);
    }

    @Test
    void resetPasswordSaved_ThrowsIllegalArgumentException_WhenPasswordsDoNotMatch() {
        assertThrows(IllegalArgumentException.class,
                () -> authService.resetPasswordSaved("","password", "differentPassword"));
    }

    @Test
    void resetPasswordSaved_DoesNotThrow_WhenPasswordsMatch() {
        assertDoesNotThrow(() -> authService.resetPasswordSaved("","password", "password"));
    }
}