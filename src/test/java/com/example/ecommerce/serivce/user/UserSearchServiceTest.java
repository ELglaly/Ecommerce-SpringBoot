package com.example.ecommerce.serivce.user;

import com.example.ecommerce.entity.user.UserContact;
import com.example.ecommerce.entity.user.UserSecurity;
import com.example.ecommerce.exceptions.user.AccountIsNotActivatedException;
import com.example.ecommerce.exceptions.user.UserNotFoundException;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.repository.user.UserRepository;
import com.example.ecommerce.request.user.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserSearchServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;
    private LoginRequest loginRequest;
    private UserSecurity userSecurity;

    @BeforeEach
    void setUp() {
        userSecurity = new UserSecurity();
        userSecurity.setHashedPassword("hashedPassword");
        userSecurity.setActivated(true);

        UserContact userContact = new UserContact();
        userContact.setEmail("test@example.com");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUserSecurity(userSecurity);
        user.setUserContact(userContact);

        userDTO = new UserDTO("Test", "User", "testuser",
                Collections.emptyList(), null, null);

        loginRequest = new LoginRequest("","");
    }

    @Test
    void getUserDtoById_ReturnsUserDTO_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);

        // When - Execute the method under test
        UserDTO result = userService.getUserDtoById(1L);

        // Then - Comprehensive assertions
        assertThat(result)
                .isNotNull()
                .isInstanceOf(UserDTO.class)
                .extracting(UserDTO::firstName, UserDTO::lastName, UserDTO::username)
                .containsExactly("Test", "User", "testuser");

        // Verify specific fields if neede
        assertThat(result.username()).isEqualTo("testuser");

        // Verify interactions with explicit times
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toDto(user);

        // Ensure no other interactions occurred
        verifyNoMoreInteractions(userRepository, userMapper);
    }

    @Test
    void getUserDtoById_ThrowsUserNotFoundException_WhenUserNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserDtoById(1L));
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_ReturnsUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_ThrowsUserNotFoundException_WhenUserNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserByEmail_ReturnsUserDTO_WhenUserExists() {
        when(userRepository.findByUserContactEmail("test@example.com", User.class)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserByEmail("test@example.com");

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userRepository).findByUserContactEmail("test@example.com", User.class);
        verify(userMapper).toDto(user);
    }

    @Test
    void getUserByEmail_ThrowsUserNotFoundException_WhenUserNotExists() {
        when(userRepository.findByUserContactEmail("test@example.com", User.class)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail("test@example.com"));
        verify(userRepository).findByUserContactEmail("test@example.com", User.class);
    }

    @Test
    void getUserByUsername_ReturnsUserDTO_WhenUserExists() {
        when(userRepository.findByUsername("testuser", User.class)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserByUsername("testuser");

        assertNotNull(result);
        assertEquals(userDTO, result);
        verify(userRepository).findByUsername("testuser", User.class);
        verify(userMapper).toDto(user);
    }

    @Test
    void getUserByUsername_ThrowsUserNotFoundException_WhenUserNotExists() {
        when(userRepository.findByUsername("testuser", User.class)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("testuser"));
        verify(userRepository).findByUsername("testuser", User.class);
    }



}