package com.example.Ecommerce.model.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDto {

    private Long id;
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Brand cannot be null")
    @NotBlank(message = "Brand cannot be empty")
    @Size(min = 2, max = 50, message = "Brand must be between 2 and 50 characters")
    private String brand;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be Empty")
    @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 2 decimal places")
    private BigDecimal price;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 10000, message = "Quantity cannot exceed 10,000")
    private int quantity;

    @NotNull(message = "Category cannot be null")
    private CategoryDto categoryDto;

    private List<ImageDto> ImageDto;




}
