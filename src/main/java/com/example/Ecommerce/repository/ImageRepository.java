package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Long countByProductName(String productName);

    List<Image> findImageByProductName(String productName);

    Image getImageByUrl(String url);

    Boolean existsByName(String name);

    List<Image> getImageByProductId(Long id);

    List<Image> findImageByProductId(Long productId);

    void deleteByProductId(Long productId);

    List<Image> findByProductId(Long id);

    boolean existsByUrl(String url);

    Optional<Image> findByUrl(String url);
}
