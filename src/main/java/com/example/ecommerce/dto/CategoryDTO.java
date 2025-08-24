package com.example.ecommerce.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.example.ecommerce.entity.Category}
 */
public record CategoryDTO(String name, String description, List<ImageDTO> images) implements Serializable {
}