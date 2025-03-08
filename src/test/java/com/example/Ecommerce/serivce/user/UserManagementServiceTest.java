package com.example.Ecommerce.serivce.user;

import com.example.Ecommerce.DummyObjects;
import com.example.Ecommerce.exceptions.user.UserAlreadyExistsException;
import com.example.Ecommerce.exceptions.user.UserNotFoundException;
import com.example.Ecommerce.mapper.IUserMapper;
import com.example.Ecommerce.mapper.UserMapper;
import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.model.entity.User;
import com.example.Ecommerce.repository.UserRepository;
import com.example.Ecommerce.request.user.CreateUserRequest;
import com.example.Ecommerce.request.user.UpdateUserRequest;
import com.example.Ecommerce.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private IUserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService JwtService;

    @InjectMocks
    private UserService userService;


    private User user;
    private UserDto userDto;
    private CreateUserRequest createUserRequest;
    private UpdateUserRequest updateUserRequest;

    @BeforeEach
    void setUp() {
        DummyObjects dummyObjects=new DummyObjects();
        user= DummyObjects.user1;
        userDto=DummyObjects.userDto1;
        createUserRequest=dummyObjects.createUserRequest1;
        updateUserRequest=dummyObjects.updateUserRequest1;
    }

    @Test
    void createUser_ReturnsUserDto_WhenCreateUserRequestIsValid() {
        //Arrange
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toEntityFromAddRequest(createUserRequest)).thenReturn(user);
        when(passwordEncoder.encode(createUserRequest.getPassword())).thenReturn(user.getPassword());
        when(userMapper.toDto(user)).thenReturn(userDto);

        //Act
        UserDto result=userService.createUser(createUserRequest);
        //Assert
        assertNotNull(result);
        assertEquals(userDto,result);
        verify(userRepository,times(1)).save(user);
        verify(userMapper,times(1)).toDto(user);
        verify(userMapper,times(1)).toEntityFromAddRequest(createUserRequest);
    }
    @Test
    void createUser_ThrowUserAlreadyExistsException_WhenUserNameExists() {
        //Arrange
        when(userRepository.existsByUsername(createUserRequest.getUsername())).thenReturn(true);
        //Act &Assert

        assertThrows(UserAlreadyExistsException.class, ()->{
            userService.createUser(createUserRequest);
        });
        verify(userRepository,times(1)).existsByUsername(createUserRequest.getUsername());
    }
    @Test
    void createUser_ThrowUserAlreadyExistsException_WhenEmailExists() {
        //Arrange
        when(userRepository.existsByEmail(createUserRequest.getEmail())).thenReturn(true);
        //Act &Assert

        assertThrows(UserAlreadyExistsException.class, ()->{
            userService.createUser(createUserRequest);
        });
        verify(userRepository,times(1)).existsByEmail(createUserRequest.getEmail());
    }

    @Test
    void updateUser_ReturnsUserDto_WhenCreateUserRequestIsValid() {
        UserDto updatedUser =userDto;
        updatedUser.setLastName("Updated");
        //Arrange
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toEntityFromUpdateRequest(updateUserRequest)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(updatedUser);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(JwtService.extractUserId("token")).thenReturn(1L);
        // Act
        UserDto result =userService.updateUser("token",updateUserRequest);
        //Assert
        assertNotNull(result);
        assertEquals(updatedUser,result);
        assertEquals(userDto.getLastName(),result.getLastName());
        verify(userRepository,times(1)).save(user);
        verify(userMapper,times(1)).toDto(user);
        verify(userMapper,times(1)).toEntityFromUpdateRequest(updateUserRequest);
        verify(userRepository,times(1)).findById(1L);
    }
    @Test
    void updateUser_ThrowUserNotFoundException_WhenIdDoesNotExist() {
        //Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(JwtService.extractUserId("token")).thenReturn(1L);
        //Act &Assert
        assertThrows(UserNotFoundException.class, ()->{
            userService.updateUser("token",updateUserRequest);
        });
        verify(userRepository,times(1)).findById(1L);
    }

    @Test
    void updateUser_ThrowUserAlreadyExistsException_WhenUserNameExists() {

        //Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(userRepository.existsByUsername(createUserRequest.getUsername())).thenReturn(true);
        when(JwtService.extractUserId("token")).thenReturn(1L);
        //Act &Assert
        assertThrows(UserAlreadyExistsException.class, ()->{
            userService.updateUser("token",updateUserRequest);
        });
        verify(userRepository,times(1)).existsByUsername(createUserRequest.getUsername());
        verify(userRepository,times(1)).findById(1L);
    }
    @Test
    void updateUser_ThrowUserAlreadyExistsException_WhenEmailExists() {
        //Arrange
        user.setId(1L);
        updateUserRequest.setEmail("sherif@gmail.com");
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(userRepository.existsByEmail(updateUserRequest.getEmail())).thenReturn(true);
        //Act &Assert
        assertThrows(UserAlreadyExistsException.class, ()->{
            userService.updateUser("token",updateUserRequest);
        });
        verify(userRepository,times(1)).findById(1L);
        verify(userRepository,times(1)).existsByEmail(updateUserRequest.getEmail());

    }

    @Test
    void deleteUser_DeletesUser_WhenUserExists() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(JwtService.extractUserId("token")).thenReturn(1L);

        // Act
        userService.deleteUser("token");

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_ThrowUserNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(JwtService.extractUserId("token")).thenReturn(1L);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser("token");
        });
        verify(userRepository, times(1)).findById(1L);
    }
}