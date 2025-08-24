package com.example.ecommerce.dto.cart;

import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link com.example.ecommerce.entity.CartItem}
 */
public record CartItemDTO(@Min(message = "Quantity must be at least 1", value = 1)
                          @Max(message = "Quantity cannot exceed 1000", value = 1000) int quantity, BigDecimal unitPrice, BigDecimal totalPrice,
                          @NotNull(message = "Product is mandatory") CartItemDTO.ProductDTO product) implements Serializable {
    /**
     * DTO for {@link com.example.ecommerce.entity.Product}
     */
    public record ProductDTO(@NotBlank(message = "Name is StorageType") String name, @NotBlank(message = "Brand is StorageType") String brand, @Size(message = "Description cannot exceed 500 characters", max = 500) String description, List<ImageDto> images) implements Serializable {
        /**
         * DTO for {@link com.example.ecommerce.entity.Image}
         */
        public record ImageDto(@NotBlank(message = "Storage Type is Required") String filePath) implements Serializable {
        }
    }
}