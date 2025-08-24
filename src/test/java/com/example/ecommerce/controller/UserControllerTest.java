package com.example.ecommerce.controller;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.repository.user.UserRepository;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.serivce.user.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// src/test/java/com/example/ecommerce/controller/AuthControllerTest.java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("UserController Integration Tests - Real Calls")
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService; // Real service, not mock

    @Autowired
    private UserController userController; // Real controller, not mock

    private User existingUser;

    @BeforeEach
    void setUp() {
        // Clean database before each test
      //  userRepository.deleteAll();

        // Create real test user in database

    }

        @Test
        @DisplayName("Real test: 404 for non-existent user")
        @Order(1)
        void realTest_404_NonExistentUser() {
            // Given - Ensure database is clean
            assertThat(userRepository.count()).isEqualTo(0);

            // When - Real HTTP call to non-existent user
            ResponseEntity<ApiResponse> response = restTemplate.getForEntity(
                    "/api/users/12345", ApiResponse.class
            );

            // Then - Real 404 from actual database miss
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    @AfterEach
    void tearDown() {
        // Clean up database after each test
       // userRepository.deleteAll();
    }
}
