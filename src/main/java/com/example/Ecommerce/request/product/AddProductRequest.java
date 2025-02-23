package com.example.Ecommerce.request.product;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

import static com.example.Ecommerce.constants.ErrorMessages.ProductError.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductRequest {

    @NotNull(message = NAME_NULL)
    @Size(min = 2, max = 100, message = NAME_SIZE)
    private String name;

    @NotNull(message = BRAND_NULL)
    @Size(min = 2, max = 50, message =BRAND_SIZE)
    private String brand;

    @NotNull(message = DESCRIPTION_NULL)
    @Size(min = 5, max = 500, message = DESCRIPTION_SIZE)
    private String description;

    @NotNull(message = PRICE_NULL)
    @DecimalMin(value = "0.01", message = PRICE_MIN)
    @Digits(integer = 10, fraction = 2, message = PRICE_DIGITS)
    private BigDecimal price;

    @Min(value = 1, message = QUANTITY_MIN)
    @Max(value = 10000, message = QUANTITY_MAX)
    private int quantity;

    @NotNull(message = CATEGORY_NULL)
    private String category;

}
