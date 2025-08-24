package com.example.ecommerce.controller.AuthControllerTest;

import com.example.ecommerce.controller.UserController;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.enums.PhoneNumberType;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.repository.user.UserRepository;
import com.example.ecommerce.request.user.AddAddressRequest;
import com.example.ecommerce.request.user.AddPhoneNumberRequest;
import com.example.ecommerce.request.user.CreateUserRequest;
import com.example.ecommerce.request.user.LoginRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.response.ErrorResponse;
import com.example.ecommerce.serivce.user.AuthService; // FIXED: service instead of serivce
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static com.example.ecommerce.constants.ErrorMessages.UserError.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// src/test/java/com/example/ecommerce/controller/AuthControllerTest.java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("UserController Integration Tests - Real Calls")
@Profile("test")
class AuthControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthService authService; // Real service, not mock

    @Autowired
    private UserController userController; // Real controller, not mock

    @LocalServerPort
    private int port;

    CreateUserRequest createUserRequest;
    AddPhoneNumberRequest addPhoneNumberRequest;
    AddAddressRequest addAddressRequest;
    private User existingUser;
    private String baseUrl;
    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        // Configure to not throw exceptions for 4xx/5xx status codes
        addAddressRequest = new AddAddressRequest(
                "123 Test St", "City", "Country", "55555", "Egypt"
        );
        addPhoneNumberRequest = AddPhoneNumberRequest.builder()
                .countryCode("+20")
                .phoneNumberType(PhoneNumberType.MOBILE)
                .number("0102638652")
                .build();
        createUserRequest = new CreateUserRequest(
                "ValidUser",
                "Password123@#123",
                "sherif@gmail.com",
                Set.of(addPhoneNumberRequest),
                List.of(addAddressRequest)
        );

        userRepository.deleteAll();
    }
    @Test
    void testLogin_Success() throws Exception {
        LoginRequest request = new LoginRequest("test@example.com", "password123");
        UserDTO userDTO = new UserDTO();

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/auth/login",
                request,
                ApiResponse.class
        );
        // FIXED: Use .andExpect() for assertions
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull()
                .extracting(ApiResponse::getMessage, ApiResponse::getData)
                .containsExactly("Login successful", userDTO);
    }

    @Test
    void testRegister_Success() throws Exception {


    }


    @Test
    void testLoginWithGoogle() throws Exception {

    }

    @Test
    void testDeleteUser_Success() throws Exception {
        doNothing().when(authService).deleteUser(anyString());


    }

    @Test
    void testActivateUser_Success() throws Exception {
        doNothing().when(authService).activateUser(anyString());


    }

    @Test
    void testResetPassword_Success() throws Exception {
        doNothing().when(authService).resetPassword(anyString());
   }

    @Test
    void testResetPasswordSaved_Success() throws Exception {
        doNothing().when(authService).resetPasswordSaved(anyString(), anyString(), anyString());
    }

    @Test
    void testChangePassword_Success() throws Exception {
        doNothing().when(authService).changePassword(anyString(), anyString(), anyString(), anyString());

    }

    @ParameterizedTest
    @DisplayName("Register with invalid password should return validation error")
    @ValueSource(strings = { "short", "nouppercase1!", "NOLOWERCASE1!",
            "NoNumber!", "NoSpecialChar1", "Short1!", "!" , "ValidPassword@12345678901234567890"
            , "ValidPassword@12345678901234567890123456789012345678901234567890",
            "ValidPassword@1234567890123456789012345678901234567890123456789012345678901234567890"
    })
    void testRegister_InvalidPassword_ShouldReturnValidationError(String invalidPassword) {
        // Arrange
        CreateUserRequest req = new CreateUserRequest(
                createUserRequest.username(),
                invalidPassword, // Invalid password
                createUserRequest.email(),
                createUserRequest.phoneNumber(),
                createUserRequest.address()
        );
        // Act
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                req,
                ApiResponse.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains(PASSWORD_PATTERN);
        assertThat(response.getBody().getData()).isNull();

    }
    @ParameterizedTest(name = "{index} - Register with invalid email should return validation error")
    @ValueSource(strings = { "invalid-email", "user@.com", "user#domain#",
            "user@domain.", "user@domain..com", "user@domain,com", "user@domain,com",
            "user@domain..com","sh"})
    void testRegister_InvalidEmail_ShouldReturnValidationError(String invalidEmail) {
        // Arrange
        CreateUserRequest req = new CreateUserRequest(
                createUserRequest.username(),
                createUserRequest.password(),
                invalidEmail, // Invalid email
                createUserRequest.phoneNumber(),
                createUserRequest.address()
        );
        // Act
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                req,
                ApiResponse.class
        );
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains(EMAIL_INVALID);
        assertThat(response.getBody().getData()).isNull();
    }

    @ParameterizedTest(name = "{index} - Register with null or empty email should return validation error")
    @ValueSource(strings = { "" })
    @DisplayName("Register with null or empty email should return validation error")
    void testRegister_NullOrEmptyEmail_ShouldReturnValidationError(String nullOrEmptyEmail) {
        // Arrange
        CreateUserRequest req = new CreateUserRequest(
                createUserRequest.username(),
                createUserRequest.password(),
                nullOrEmptyEmail, // Null or empty email
                createUserRequest.phoneNumber(),
                createUserRequest.address()
        );
        // Act
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                req,
                ApiResponse.class
        );
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains(EMAIL_EMPTY);
        assertThat(response.getBody().getData()).isNull();
    }

    @ParameterizedTest(name = "{index} - Register with invalid username should return validation error")
    @ValueSource(strings = { "a!b@c", "user name", "user name with spaces", "user@name", "user#name", "user$name", "user%name",
            "user^name", "user&name", "user*name", "user(name)", "user)name", "user+name",
            "user=name", "user{name}", "user}name", "user|name", "user\\name", "user/name",
            "user?name"," ", "   ", "\t", "\n", "\r" ,"\t\n\r "})
    void testRegister_InvalidUsername_ShouldReturnValidationError(String invalidUsername) {
        // Arrange
        CreateUserRequest req = new CreateUserRequest(
                invalidUsername, // Invalid username
                createUserRequest.password(),
                createUserRequest.email(),
                createUserRequest.phoneNumber(),
                createUserRequest.address()
        );

        // Act
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                req,
                ApiResponse.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains(USERNAME_PATTERN);
        assertThat(response.getBody().getData()).isNull();
    }

    @ParameterizedTest(name = "{index} - Register with null or empty username should return validation error")
    @ValueSource(strings = { "" })
    @DisplayName("Register with null or empty username should return validation error")
    void testRegister_NullOrEmptyUsername_ShouldReturnValidationError(String nullOrEmptyUsername) {
        // Arrange
        CreateUserRequest req = new CreateUserRequest(
                nullOrEmptyUsername, // Null or empty username
                createUserRequest.password(),
                createUserRequest.email(),
                createUserRequest.phoneNumber(),
                createUserRequest.address()
        );
        // Act
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                req,
                ApiResponse.class
        );
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains(USERNAME_EMPTY);
        assertThat(response.getBody().getData()).isNull();
    }
    @ParameterizedTest(name = "{index} - Register with size not convenient username should return validation error")
    @ValueSource(strings = {"a", "ab",
            "                         Smmsksdmx,c lsmdisci skdisfi              above50char" })
    @DisplayName("Register with null or empty username should return validation error")
        void testRegister_SizeNotConvenientUsername_ShouldReturnValidationError(String sizeNotConvenientUsername) {
        // Arrange
        CreateUserRequest req = new CreateUserRequest(
                sizeNotConvenientUsername, // Null or empty username
                createUserRequest.password(),
                createUserRequest.email(),
                createUserRequest.phoneNumber(),
                createUserRequest.address()
        );
        // Act
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                req,
                ApiResponse.class
        );
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains(USERNAME_SIZE);
        assertThat(response.getBody().getData()).isNull();
    }

    @Test
    @DisplayName("Register with existing email should return conflict error")
    void testRegister_ExistingEmail_ShouldReturnConflictError() {
        // Arrange
        CreateUserRequest req = new CreateUserRequest(
                createUserRequest.username(),
                createUserRequest.password(),
                createUserRequest.email(),
                createUserRequest.phoneNumber(),
                createUserRequest.address()
        );
        // Save the user to ensure it exists
        User user = userMapper.toEntity(req);
        userRepository.save(user);

        // Act
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                req,
                ErrorResponse.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody())
                .isNotNull()
                .extracting("message", "statusCode")
                .contains(
                        String.format("User with email %s already exists", createUserRequest.email()),
                        HttpStatus.CONFLICT.value()
                );
}

    @Test
    @DisplayName("Register with existing username should return conflict error")
    void testRegister_ExistingUsername_ShouldReturnConflictError() {
        // Arrange
        CreateUserRequest req = new CreateUserRequest(
                createUserRequest.username(),
                createUserRequest.password(),
                "valid@gmail.com",
                createUserRequest.phoneNumber(),
                createUserRequest.address()
        );
        // Save the user to ensure it exists
        User user = userMapper.toEntity(createUserRequest);
        userRepository.save(user);

        // Act
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                req,
                ErrorResponse.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody())
                .isNotNull()
                .extracting("message", "statusCode")
                .contains(
                        String.format("User with username %s already exists", createUserRequest.username()),
                        HttpStatus.CONFLICT.value()
                );
    }
}
