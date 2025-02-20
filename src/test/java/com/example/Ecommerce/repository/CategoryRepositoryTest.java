package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        // Create and persist test categories with descriptions
        category1 = new Category();
        category1.setName("Electronics");
        category1.setDescription("Category for electronic devices"); // Add description
        entityManager.persist(category1);

        category2 = new Category();
        category2.setName("Clothing");
        category2.setDescription("Category for clothing and apparel"); // Add description
        entityManager.persist(category2);

        entityManager.flush();
    }

    @Test
    void testFindByName() {
        // Test finding a category by name
        Optional<Category> foundCategory = Optional.ofNullable(categoryRepository.findByName("Electronics"));

        // Verify the results
        assertTrue(foundCategory.isPresent());
        assertEquals("Electronics", foundCategory.get().getName());
        assertEquals("Category for electronic devices", foundCategory.get().getDescription()); // Verify description
    }

    @Test
    void testFindByName_NotFound() {
        // Test finding a category by a non-existent name
        Optional<Category> foundCategory = Optional.ofNullable(categoryRepository.findByName("NonExistentCategory"));

        // Verify that no category is found
        assertFalse(foundCategory.isPresent());
    }

    @Test
    void testExistsByName() {
        // Test if a category exists by name
        boolean exists = categoryRepository.existsByName("Clothing");

        // Verify the results
        assertTrue(exists);
    }

    @Test
    void testExistsByName_NotFound() {
        // Test if a category exists by a non-existent name
        boolean exists = categoryRepository.existsByName("NonExistentCategory");

        // Verify that the category does not exist
        assertFalse(exists);
    }

    @Test
    void testCountByName() {
        // Test counting categories by name
        long count = categoryRepository.countByName("Electronics");

        // Verify the results
        assertEquals(1, count);
    }

    @Test
    void testCountByName_NotFound() {
        // Test counting categories by a non-existent name
        long count = categoryRepository.countByName("NonExistentCategory");

        // Verify that the count is zero
        assertEquals(0, count);
    }

    @Test
    void testDescriptionField() {
        // Test the description field of a category
        Optional<Category> foundCategory = Optional.ofNullable(categoryRepository.findByName("Clothing"));

        // Verify the description
        assertTrue(foundCategory.isPresent());
        assertEquals("Category for clothing and apparel", foundCategory.get().getDescription());
    }
}