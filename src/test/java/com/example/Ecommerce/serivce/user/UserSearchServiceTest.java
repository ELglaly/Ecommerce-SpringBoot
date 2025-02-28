package com.example.Ecommerce.serivce.user;

import com.example.Ecommerce.DummyObjects;
import com.example.Ecommerce.exceptions.user.UserNotFoundException;
import com.example.Ecommerce.mapper.IUserMapper;
import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.model.entity.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.request.user.CreateUserRequest;
import com.example.Ecommerce.request.user.UpdateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSearchServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private IUserMapper userMapper;

    @InjectMocks
    private UserService userService;


    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        DummyObjects dummyObjects=new DummyObjects();
        user= DummyObjects.user1;
        userDto=DummyObjects.userDto1;
    }

    @Test
    void getUserDtoById_ReturnsUserDto_WhenUserExists() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userService.getUserDtoById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(userDto, result);
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    void getUserDtoById_ThrowUserNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserDtoById(1L);
        });
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserByEmail_ReturnsUserDto_WhenUserExists() {
        // Arrange
        when(userRepository.findByEmail("mohamedahmed@example.com")).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userService.getUserByEmail("mohamedahmed@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(userDto, result);
        verify(userRepository, times(1)).findByEmail("mohamedahmed@example.com");
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    void getUserByEmail_ThrowUserNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByEmail("nonexistent@example.com");
        });
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    void getUserByUsername_ReturnsUserDto_WhenUserExists() {
        // Arrange
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userService.getUserByUsername(user.getUsername());

        // Assert
        assertNotNull(result);
        assertEquals(userDto, result);
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    void getUserByUsername_ThrowUserNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByUsername("nonexistentuser");
        });
        verify(userRepository, times(1)).findByUsername("nonexistentuser");
    }



    @Test
    void login() {
    }
}