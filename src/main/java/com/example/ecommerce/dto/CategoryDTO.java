package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.example.ecommerce.entity.Category}
 */
public record CategoryDTO(@Pattern(message = "name must contain only letters", regexp = "^[a-zA-Z]+$")
                          @NotBlank(message = "Name is required") String name,
                          @Size(message = "Description must be between 3 and 50 characters", min = 3, max = 500)
                          @Pattern(message = "Description must contain only letters", regexp = "^[a-zA-Z]+$")
                          @NotBlank(message = "Description is required") String description, List<ImageDTO> images) implements Serializable {
  }