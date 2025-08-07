package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link com.example.ecommerce.entity.Product}
 */
public record ProductDTO(@NotBlank(message = "Name is StorageType") String name, @NotBlank(message = "Brand is StorageType") String brand, @Size(message = "Description cannot exceed 500 characters", max = 500) String description, @NotNull(message = "Price is Required") BigDecimal price, List<ImageDTO> images) implements Serializable {
  }