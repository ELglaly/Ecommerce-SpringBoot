package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.entity.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setName("ADMIN");
        role = roleRepository.save(role);
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void findByName_ReturnsRole() {
        Optional<Role> foundRole = Optional.ofNullable(roleRepository.findByName("ADMIN"));

        assertTrue(foundRole.isPresent());
        assertEquals("ADMIN", foundRole.get().getName());
    }

    @Test
    void findByName_ReturnsEmpty_WhenRoleNotFound() {
        Optional<Role> foundRole = Optional.ofNullable(roleRepository.findByName("USER"));

        assertFalse(foundRole.isPresent());
    }

    @Test
    void deleteByName_RemovesRole() {
        roleRepository.deleteByName("ADMIN");

        Optional<Role> foundRole = Optional.ofNullable(roleRepository.findByName("ADMIN"));
        assertFalse(foundRole.isPresent());
    }

    @Test
    void deleteByName_DoesNothing_WhenRoleNotFound() {
        // Ensure there is only one role before attempting to delete a non-existent one
        long countBefore = roleRepository.count();
        roleRepository.deleteByName("NON_EXISTENT_ROLE");
        long countAfter = roleRepository.count();

        assertEquals(countBefore, countAfter);
    }
}
