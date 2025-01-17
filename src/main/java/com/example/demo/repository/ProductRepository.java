package com.example.demo.repository;

import com.example.demo.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String name);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByNameAndCategoryName(String name, String category);

    int countByCategoryName(String category);

    int countByBrand(String brand);

    int countByName(String name);

    boolean existsByName(String name);
}
