package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.entity.Image;
import com.example.Ecommerce.model.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ImageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ImageRepository imageRepository;

    private Product product1;
    private Product product2;
    private Image image1;
    private Image image2;
    private Image image3;

    @BeforeEach
    void setUp() {
        // Create and persist products
        product1 = new Product();
        product1.setName("Product 1");
        entityManager.persist(product1);

        product2 = new Product();
        product2.setName("Product 2");
        entityManager.persist(product2);

        // Create and persist images
        image1 = new Image();
        image1.setName("Image 1");
        image1.setUrl("http://example.com/image1.jpg");
        image1.setProduct(product1);
        entityManager.persist(image1);

        image2 = new Image();
        image2.setName("Image 2");
        image2.setUrl("http://example.com/image2.jpg");
        image2.setProduct(product1);
        entityManager.persist(image2);

        image3 = new Image();
        image3.setName("Image 3");
        image3.setUrl("http://example.com/image3.jpg");
        image3.setProduct(product2);
        entityManager.persist(image3);

        entityManager.flush();
    }

    @Test
    void testCountByProductName_ReturnsNumber() {
        // Test counting images by product name
        long count = imageRepository.countByProductName("Product 1");

        // Verify the results
        assertEquals(2, count); // 2 images for Product 1
    }

    @Test
    void testFindImageByProductName_ReturnsListOfImages() {
        // Test finding images by product name
        List<Image> images = imageRepository.findImageByProductName("Product 1");

        // Verify the results
        assertEquals(2, images.size());
        assertTrue(images.contains(image1));
        assertTrue(images.contains(image2));
    }

    @Test
    void testFindByUrl_ReturnsImage() {
        // Test finding an image by URL
        Optional<Image> foundImage = imageRepository.findByUrl("http://example.com/image1.jpg");

        // Verify the results
        assertTrue(foundImage.isPresent());
        assertEquals("http://example.com/image1.jpg", foundImage.get().getUrl());
    }

    @Test
    void testExistsByUrl_ReturnsTrue() {
        // Test if an image exists by URL
        boolean exists = imageRepository.existsByUrl("http://example.com/image2.jpg");

        // Verify the results
        assertTrue(exists);
    }

    @Test
    void testFindByProductId_ReturnsListOfImages() {
        // Test finding images by product ID
        List<Image> images = imageRepository.findByProductId(product1.getId());

        // Verify the results
        assertEquals(2, images.size());
        assertTrue(images.contains(image1));
        assertTrue(images.contains(image2));
    }

    @Test
    void testDeleteByProductId_ReturnsNothing() {
        // Test deleting images by product ID
        imageRepository.deleteByProductId(product1.getId());

        // Verify that the images are deleted
        List<Image> remainingImages = imageRepository.findByProductId(product1.getId());
        assertTrue(remainingImages.isEmpty());
    }
}