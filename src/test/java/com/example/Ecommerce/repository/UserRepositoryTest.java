package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User.Builder()
                .firstName("sherif")
                .lastName("ashraf")
                .birthDate(new Date())
                .username("sherif12")
                .password("password123")
                .email("sherif@example.com")
                .build();
    }

    // Test Case 1: Save a valid user
    @Test
    void testSaveUser_ReturnsUser() {
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        assertEquals("sherif", savedUser.getFirstName());
        assertEquals("ashraf", savedUser.getLastName());
        assertEquals("sherif12", savedUser.getUsername());
        assertEquals("sherif@example.com", savedUser.getEmail());
    }

    // Test Case 2: Find a user by username
    @Test
    void testFindByUsername_ReturnsUser() {
        entityManager.persist(user);
        User foundUser = userRepository.findByUsername("sherif12");
        assertNotNull(foundUser);
        assertEquals("sherif12", foundUser.getUsername());
    }

    // Test Case 3: Find a user by email
    @Test
    void testFindByEmail_ReturnsUser() {
        entityManager.persist(user);
        User foundUser = userRepository.findByEmail("sherif@example.com");
        assertNotNull(foundUser);
        assertEquals("sherif@example.com", foundUser.getEmail());
    }

    // Test Case 4: Check if a user exists by username
    @Test
    void testExistsByUsername_ReturnsTrue() {
        entityManager.persist(user);
        boolean exists = userRepository.existsByUsername("sherif12");
        assertTrue(exists);
    }

    // Test Case 5: Check if a user exists by email
    @Test
    void testExistsByEmail_ReturnsTrue() {
        entityManager.persist(user);
        boolean exists = userRepository.existsByEmail("sherif@example.com");
        assertTrue(exists);
    }

    // Test Case 6: Delete a user
    @Test
    void testDeleteUser_ReturnsFalse() {
        User savedUser = entityManager.persist(user);
        userRepository.delete(savedUser);
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertFalse(deletedUser.isPresent());
    }

    // Test Case 7: Update a user
    @Test
    void testUpdateUser_ReturnsUpdatedUser() {
        User savedUser = entityManager.persist(user);
        savedUser.setFirstName("User");
        savedUser.setLastName("ali");
        userRepository.save(savedUser);

        Optional<User> updatedUser = userRepository.findById(savedUser.getId());
        assertTrue(updatedUser.isPresent());
        assertEquals("User", updatedUser.get().getFirstName());
        assertEquals("ali", updatedUser.get().getLastName());
    }

    // Test Case 8: Save a user with a duplicate username (should fail)
    @Test
    void testSaveUserWithDuplicateUsername_ReturnsException() {
        entityManager.persist(user);

        User duplicateUser = new User.Builder()
                .firstName("User")
                .lastName("ali")
                .birthDate(new Date())
                .username("sherif12") // Duplicate username
                .password("password456")
                .email("userali@example.com")
                .build();


       assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(duplicateUser));
    }

    // Test Case 9: Save a user with a duplicate email (should fail)
    @Test
    void testSaveUserWithDuplicateEmail_ReturnsException() {
        entityManager.persist(user);

        User duplicateUser = new User.Builder()
                .firstName("User")
                .lastName("ali")
                .birthDate(new Date())
                .username("userali")
                .password("password456")
                .email("sherif@example.com") // Duplicate email
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(duplicateUser));
    }

    // Test Case 10: Find a user by a non-existent username
    @Test
    void testFindByNonExistentUsername_ReturnsNull() {
        User foundUser = userRepository.findByUsername("no");
        assertNull(foundUser);
    }

    // Test Case 11: Find a user by a non-existent email
    @Test
    void testFindByNonExistentEmail_ReturnsNull() {
        User foundUser = userRepository.findByEmail("no@example.com");
        assertNull(foundUser);
    }

    // Test Case 12: Save a user with null fields (should fail)
    @Test
    void testSaveUserWithNullFields_ReturnsException() {
        User invalidUser = new User.Builder()
                .username(null) // Null username
                .password(null) // Null password
                .email(null) // Null email
                .build();

        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> userRepository.save(invalidUser));
    }

    // Test Case 13: Save a user with an invalid email format (should fail)
    @Test
    void testSaveUserWithInvalidEmail_ReturnsException() {
        User invalidUser = new User.Builder()
                .firstName("sherif")
                .lastName("ashraf")
                .birthDate(new Date())
                .username("sherif12")
                .password("password123")
                .email("invalidemail") // Invalid email format
                .build();

        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> userRepository.save(invalidUser));
    }

    // Test Case 14: Save a user with a future birthdate (should fail)
    @Test
    void testSaveUserWithFutureBirthDate_ReturnsException() {
        User invalidUser = new User.Builder()
                .firstName("sherif")
                .lastName("ashraf")
                .birthDate(new Date(System.currentTimeMillis() + 100000)) // Future date
                .username("sherif12")
                .password("password123")
                .email("sherif@example.com")
                .build();

        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> userRepository.save(invalidUser));
    }

    // Test Case 15: Save a user with a very long username (should fail)
    @Test
    void testSaveUserWithLongUsername_ReturnsException() {
        User invalidUser = new User.Builder()
                .firstName("sherif")
                .lastName("ashraf")
                .birthDate(new Date())
                .username("a".repeat(256)) // Username exceeds 255 characters
                .password("password123")
                .email("sherif@example.com")
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(invalidUser));
    }
}