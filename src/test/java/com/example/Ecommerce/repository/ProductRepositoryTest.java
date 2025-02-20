package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.entity.Category;
import com.example.Ecommerce.model.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    private Category category1;
    private Category category2;
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void setUp() {
        // Create and persist categories
        category1 = new Category();
        category1.setName("Electronics");
        entityManager.persist(category1);

        category2 = new Category();
        category2.setName("Clothing");
        entityManager.persist(category2);

        // Create and persist products
        product1 = new Product();
        product1.setName("Smartphone");
        product1.setBrand("BrandA");
        product1.setCategory(category1);
        entityManager.persist(product1);

        product2 = new Product();
        product2.setName("Laptop");
        product2.setBrand("BrandB");
        product2.setCategory(category1);
        entityManager.persist(product2);

        product3 = new Product();
        product3.setName("T-Shirt");
        product3.setBrand("BrandC");
        product3.setCategory(category2);
        entityManager.persist(product3);

        entityManager.flush();
    }

    @Test
    void testFindByName() {
        // Test finding products by name
        List<Product> products = productRepository.findByName("Smartphone");

        // Verify the results
        assertEquals(1, products.size());
        assertEquals("Smartphone", products.get(0).getName());
    }

    @Test
    void testFindByBrandAndName() {
        // Test finding products by brand and name
        List<Product> products = productRepository.findByBrandAndName("BrandA", "Smartphone");

        // Verify the results
        assertEquals(1, products.size());
        assertEquals("Smartphone", products.get(0).getName());
        assertEquals("BrandA", products.get(0).getBrand());
    }

    @Test
    void testFindByCategoryName() {
        // Test finding products by category name
        List<Product> products = productRepository.findByCategoryName("Electronics");

        // Verify the results
        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    void testFindByBrand() {
        // Test finding products by brand
        List<Product> products = productRepository.findByBrand("BrandB");

        // Verify the results
        assertEquals(1, products.size());
        assertEquals("Laptop", products.get(0).getName());
        assertEquals("BrandB", products.get(0).getBrand());
    }

    @Test
    void testFindByCategoryNameAndBrand() {
        // Test finding products by category name and brand
        List<Product> products = productRepository.findByCategoryNameAndBrand("Electronics", "BrandA");

        // Verify the results
        assertEquals(1, products.size());
        assertEquals("Smartphone", products.get(0).getName());
        assertEquals("BrandA", products.get(0).getBrand());
    }

    @Test
    void testFindByNameAndCategoryName() {
        // Test finding products by name and category name
        List<Product> products = productRepository.findByNameAndCategoryName("Laptop", "Electronics");

        // Verify the results
        assertEquals(1, products.size());
        assertEquals("Laptop", products.get(0).getName());
        assertEquals("Electronics", products.get(0).getCategory().getName());
    }

    @Test
    void testCountByCategoryName() {
        // Test counting products by category name
        int count = productRepository.countByCategoryName("Electronics");

        // Verify the results
        assertEquals(2, count);
    }

    @Test
    void testCountByBrand() {
        // Test counting products by brand
        int count = productRepository.countByBrand("BrandC");

        // Verify the results
        assertEquals(1, count);
    }

    @Test
    void testCountByName() {
        // Test counting products by name
        int count = productRepository.countByName("T-Shirt");

        // Verify the results
        assertEquals(1, count);
    }

    @Test
    void testExistsByName() {
        // Test if a product exists by name
        boolean exists = productRepository.existsByName("Smartphone");

        // Verify the results
        assertTrue(exists);
    }

    @Test
    void testFindByNameContaining() {
        // Test finding products by name containing a substring
        List<Product> products = productRepository.findByNameContaining("Shirt");

        // Verify the results
        assertEquals(1, products.size());
        assertEquals("T-Shirt", products.get(0).getName());
    }
}