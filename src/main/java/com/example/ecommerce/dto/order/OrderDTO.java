package com.example.ecommerce.dto.order;

import com.example.ecommerce.dto.ImageDTO;
import com.example.ecommerce.enums.OrderStatus;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link com.example.ecommerce.entity.Order}
 */
public record OrderDTO(String notes, String currencyCode, @Digits(message = "Invalid Tax format", integer = 8, fraction = 2) BigDecimal taxAmount, @NotNull OrderStatus orderStatus, @NotNull @Digits(message = "Invalid price format", integer = 8, fraction = 2) BigDecimal orderTotalPrice, @Size(message = "Order must have between 1 and 100 items", min = 1, max = 100) Set<OrderItemDto> orderItems) implements Serializable {
    /**
     * DTO for {@link com.example.ecommerce.entity.OrderItem}
     */
    public record OrderItemDto(@Min(message = "Quantity must be at least 1", value = 1) @Max(message = "Quantity cannot exceed 1000", value = 1000) int quantity, BigDecimal unitPrice, BigDecimal totalPrice, @NotNull(message = "Product is mandatory") OrderDTO.OrderItemDto.ProductDTO product) implements Serializable {
        /**
         * DTO for {@link com.example.ecommerce.entity.Product}
         */
        public record ProductDTO(@NotBlank(message = "Name is StorageType") String name, @NotBlank(message = "Brand is StorageType") String brand, @Size(message = "Description cannot exceed 500 characters", max = 500) String description, CategoryDTO category) implements Serializable {
            /**
             * DTO for {@link com.example.ecommerce.entity.Category}
             */
            public record CategoryDTO(@Pattern(message = "name must contain only letters", regexp = "^[a-zA-Z]+$")
                                      @NotBlank(message = "Name is required") String name, List<ImageDTO> images) implements Serializable {

            }
        }
    }
}