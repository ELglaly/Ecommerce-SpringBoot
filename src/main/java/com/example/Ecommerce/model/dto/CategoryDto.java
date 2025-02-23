package com.example.Ecommerce.model.dto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;



@Data
@Builder
//@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryDto {

    private Long id;
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Pattern(regexp = "^[^\\d]*$", message = "Name must not contain digits")
    private String name;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be Empty")
    @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
    private String description;
    private List<ProductDto> productsDto;

    private List<ImageDto> imagesDto;

}
