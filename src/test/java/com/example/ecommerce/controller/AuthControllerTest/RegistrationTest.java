package com.example.ecommerce.controller.AuthControllerTest;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.enums.PhoneNumberType;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.repository.user.UserRepository;
import com.example.ecommerce.request.user.AddAddressRequest;
import com.example.ecommerce.request.user.AddPhoneNumberRequest;
import com.example.ecommerce.request.user.CreateUserRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.response.ErrorResponse;
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
import org.springframework.web.reactive.function.server.EntityResponse;

import java.util.*;

import static com.example.ecommerce.constants.ErrorMessages.UserError.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Registration Integration Tests - Real Calls")
@Profile("test")
class RegistrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @LocalServerPort
    private int port;

    CreateUserRequest createUserRequest;
    AddPhoneNumberRequest addPhoneNumberRequest;
    AddAddressRequest addAddressRequest;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        // Configure to not throw exceptions for 4xx/5xx status codes
        addAddressRequest = new AddAddressRequest(
                "123 Test St", "City", "Country", "55555", "Egypt"
        );
        addPhoneNumberRequest = AddPhoneNumberRequest.builder()
                .number("010263864")
                .countryCode("+20")
                .phoneNumberType(PhoneNumberType.MOBILE)
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

    @ParameterizedTest(name = "{index} - Register with password \"{0}\" ")
    @DisplayName("Register with invalid password should return validation error")
    @ValueSource(strings = {"short", "nouppercase1!", "NOLOWERCASE1!",
            "NoNumber!", "NoSpecialChar1", "Short1!", "!", "ValidPassword@12345678901234567890"
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

    @ParameterizedTest(name = "{index} - Register with Email \"{0}\"")
    @ValueSource(strings = {"invalid-email", "user@.com", "user#domain#",
            "user@domain.", "user@domain..com", "user@domain,com", "user@domain,com",
            "user@domain..com", "sh"})
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

    @ParameterizedTest(name = "{index} - Register with Email \"{0}\"")
    @ValueSource(strings = {""})
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

    @ParameterizedTest(name = "{index} - Register with username \"{0}\"")
    @ValueSource(strings = {"a!b@c", "user name", "user name with spaces", "user@name", "user#name", "user$name", "user%name",
            "user^name", "user&name", "user*name", "user(name)", "user)name", "user+name",
            "user=name", "user{name}", "user}name", "user|name", "user\\name", "user/name",
            "user?name", "   "})
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

    @ParameterizedTest(name = "{index} - Register with username \"{0}\"")
    @ValueSource(strings = {" "})
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

    @ParameterizedTest(name = "{index} - Register with username \"{0}\"")   @ValueSource(strings = {"a", "ab",
            "AliSheriabove5S_55555555555555555555555555555555555550char"})
    @DisplayName("Register with inconvenient username size should return validation error")
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
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                req,
                ErrorResponse.class
        );
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).contains(USERNAME_SIZE);
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

    @Test
    @DisplayName("Register with valid User")
    void testRegister_ValidUser_ShouldReturnValidUserDTO() {

        // Act
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                createUserRequest,
                ApiResponse.class
        );

        System.out.println(Objects.requireNonNull(response.getBody()).getData().toString());
        System.out.println(Objects.requireNonNull(response.getBody()).getData().getClass());

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody())
                .isNotNull();
        assertThat(response.getBody().getData())
                .isNotNull()
                .hasFieldOrPropertyWithValue("username", createUserRequest.username());

    }


    @ParameterizedTest(name = "{index} Phone number \"{0}\"")
    @DisplayName("Register With invalid Phone number")
    @ValueSource(strings = {"1", "22", "333", "4444", "55555", "666666", "1616161616161616",
            "sherif101", "010#45SH", "+25185358", "********", "+-*/+--*/+/", " 010268*854"})
    void testRegister_InValidPhoneNumber_ShouldReturnErrorMessage(String inValidPhoneNumber) {
        //Arrange
        addPhoneNumberRequest = AddPhoneNumberRequest.builder()
                .number(inValidPhoneNumber)
                .countryCode("+20")
                .phoneNumberType(PhoneNumberType.MOBILE)
                .build();

        createUserRequest = CreateUserRequest.builder()
                .username("sherif")
                .email("sherif@gmail.com")
                .password("Sherfi@#010")
                .phoneNumber(Set.of(addPhoneNumberRequest))
                .build();
        // Act

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                createUserRequest,
                ErrorResponse.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .isNotNull().extracting(ErrorResponse::message)
                .isEqualTo("Invalid phone number");

    }


    @ParameterizedTest(name = "{index} Phone number \"{0}\"")
    @DisplayName("Register With Empty Phone number")
    @ValueSource(strings = {"   ",})
    void testRegister_EmptyOrBlankPhoneNumber_ShouldReturnErrorMessage(String emptyOrBlankPhoneNumber) {
        //Arrange
        addPhoneNumberRequest = AddPhoneNumberRequest.builder()
                .number(emptyOrBlankPhoneNumber)
                .countryCode("+20")
                .phoneNumberType(PhoneNumberType.MOBILE)
                .build();

        createUserRequest = CreateUserRequest.builder()
                .username("sherif")
                .email("sherif@gmail.com")
                .password("Sherfi@#010")
                .phoneNumber(Set.of(addPhoneNumberRequest))
                .build();
        // Act

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                createUserRequest,
                ErrorResponse.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .isNotNull().extracting(ErrorResponse::message)
                .isEqualTo("Number is required");

    }
    @ParameterizedTest(name = "{index} Country Code \"{0}\"")
    @DisplayName("Register With inValid Country Code")
    @ValueSource(strings = {"-8888", "555","55555","88888888" , "/*+//*", "333","+666666"})
    void testRegister_InvalidCountryCode_ShouldReturnErrorMessage(String inValidCountryCode) {
        //Arrange
        addPhoneNumberRequest = AddPhoneNumberRequest.builder()
                .number("010263864")
                .countryCode(inValidCountryCode)
                .phoneNumberType(PhoneNumberType.MOBILE)
                .build();

        createUserRequest = CreateUserRequest.builder()
                .username("sherif")
                .email("sherif@gmail.com")
                .password("Sherfi@#010")
                .phoneNumber(Set.of(addPhoneNumberRequest))
                .build();
        // Act

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                createUserRequest,
                ErrorResponse.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .isNotNull().extracting(ErrorResponse::message)
                .isEqualTo("Invalid country code");

    }

    @ParameterizedTest(name = "{index} Country Code \"{0}\"")
    @DisplayName("Register With inValid Country Code")
    @ValueSource(strings = {" ", "  ","",})
    void testRegister_EmptyCountryCode_ShouldReturnErrorMessage(String emptyCountryCode) {
        //Arrange
        addPhoneNumberRequest = AddPhoneNumberRequest.builder()
                .number("010263864")
                .countryCode(emptyCountryCode)
                .phoneNumberType(PhoneNumberType.MOBILE)
                .build();

        createUserRequest = CreateUserRequest.builder()
                .username("sherif")
                .email("sherif@gmail.com")
                .password("Sherfi@#010")
                .phoneNumber(Set.of(addPhoneNumberRequest))
                .build();
        // Act

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                createUserRequest,
                ErrorResponse.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .isNotNull().extracting(ErrorResponse::message)
                .isEqualTo("Country code is required");

    }

    @ParameterizedTest(name = "{index} Country Code \"{0}\"")
    @DisplayName("Register With inValid Phone Number Type")
    @ValueSource(strings = {" ", "  ","","Mobiile", "HHOme","PhoneNumberType"})
    void testRegister_InValidCountryCode_ShouldReturnErrorMessage(String invalidPhoneNumberType) {
        //Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", "sherif");
        requestBody.put("email", "sherif@gmail.com");
        requestBody.put("password", "Sherfi@#010");

        Map<String, Object> phoneNumber = new HashMap<>();
        phoneNumber.put("number", "010263864");
        phoneNumber.put("countryCode", "+20");
        phoneNumber.put("phoneNumberType", invalidPhoneNumberType);
        requestBody.put("addPhoneNumberRequest", Set.of(phoneNumber));

        // Act

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl + "/api/v1/auth/register",
                requestBody,
                ErrorResponse.class
        );

        System.out.println(response.toString());
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .isNotNull().extracting(ErrorResponse::message)
                .isEqualTo("Invalid PhoneNumberType");

    }
}



