package com.example.ecommerce.dto;

import com.example.ecommerce.enums.StorageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link com.example.ecommerce.entity.Image}
 */
public record ImageDTO(@NotBlank(message = "Storage Type is Required") String filePath,
                       @NotNull(message = "Storage Type is Required") StorageType storageType)
        implements Serializable {
  }