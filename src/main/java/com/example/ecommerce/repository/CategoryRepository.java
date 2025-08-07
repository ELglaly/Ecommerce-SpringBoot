package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String category);

    boolean existsByName(String name);

    int countByName(@NotNull(message = "Name cannot be null") @NotBlank(message = "Name cannot be empty") @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters") @Pattern(regexp = "^[^\\d]*$", message = "Name must not contain digits") String name);
}
