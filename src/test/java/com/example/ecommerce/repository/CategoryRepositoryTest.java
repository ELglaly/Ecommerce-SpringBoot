package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category2;

    @BeforeEach
    void setUp() {
        // Create and persist test categories with descriptions
        Category category1 = new Category();
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
    void testFindByName_ReturnsCategory() {
        // Test finding a category by name
        Page<Category> foundCategory = categoryRepository.findByNameContainingOrderByNameAsc("NonExistentCategory", Pageable.ofSize(10),Category.class);

        // Verify the results
        assertFalse(foundCategory.isEmpty());
     //   assertEquals("Electronics", foundCategory.get().getName());
      //  assertEquals("Category for electronic devices", foundCategory.get().getDescription()); // Verify description
    }

    @Test
    void testFindByName__ReturnsFalse() {
        // Test finding a category by a non-existent name
        Page<Category> foundCategory = categoryRepository.findByNameContainingOrderByNameAsc("NonExistentCategory",Pageable.ofSize(10),Category.class);

        // Verify that no category is found
        assertTrue(foundCategory.isEmpty());
    }

    @Test
    void testExistsByName_ReturnsTrue() {
        // Test if a category exists by name
        boolean exists = categoryRepository.existsByName("Clothing");

        // Verify the results
        assertTrue(exists);
    }

    @Test
    void testExistsByName_ReturnsFalse() {
        // Test if a category exists by a non-existent name
        boolean exists = categoryRepository.existsByName("NonExistentCategory");

        // Verify that the category does not exist
        assertFalse(exists);
    }

    @Test
    void testCountByName_ReturnsNumber1() {
        // Test counting categories by name
        long count = categoryRepository.countByName("Electronics");

        // Verify the results
        assertEquals(1, count);
    }

    @Test
    void testCountByName_Returns0() {
        // Test counting categories by a non-existent name
        long count = categoryRepository.countByName("NonExistentCategory");

        // Verify that the count is zero
        assertEquals(0, count);
    }

}