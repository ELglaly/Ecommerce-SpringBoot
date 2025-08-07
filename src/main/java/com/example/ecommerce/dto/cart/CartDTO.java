package com.example.ecommerce.dto.cart;

import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO for {@link com.example.ecommerce.entity.Cart}
 */
public record CartDTO(String currencyCode,
                      @Digits(message = "Invalid Tax format", integer = 8, fraction = 2) BigDecimal taxAmount,
                      @NotNull @Digits(message = "Invalid price format", integer = 8, fraction = 2) BigDecimal cartTotalPrice,
                      @Size(message = "Order must have between 1 and 100 items", min = 1, max = 100) Set<CartItemDTO> cartItems) implements Serializable {
    /**
     * DTO for {@link com.example.ecommerce.entity.CartItem}
     */
    public record CartItemDTO(
            @Min(message = "Quantity must be at least 1", value = 1) @Max(message = "Quantity cannot exceed 1000", value = 1000) int quantity,
            BigDecimal unitPrice, BigDecimal totalPrice,
            @NotNull(message = "Product is mandatory") CartDTO.CartItemDTO.ProductDTO product) implements Serializable {
        /**
         * DTO for {@link com.example.ecommerce.entity.Product}
         */
        public record ProductDTO(@NotBlank(message = "Name is StorageType") String name,
                                 @NotBlank(message = "Brand is StorageType") String brand,
                                 @Size(message = "Description cannot exceed 500 characters", max = 500) String description) implements Serializable {
        }
    }
}