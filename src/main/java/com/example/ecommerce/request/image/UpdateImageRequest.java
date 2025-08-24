package com.example.ecommerce.request.image;


import com.example.ecommerce.dto.ProductDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Blob;

@Data
@AllArgsConstructor
public class UpdateImageRequest {
    @NotNull(message = "URL cannot be null")
    @NotBlank(message = "URL cannot be empty")
    private String url;

    @NotNull(message = "Image cannot be null")
    private Blob image;

    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be empty")
    @Size(min = 1, max = 255, message = "name must be between 1 and 255 characters")
    private String name;

    @NotNull(message = "Product cannot be null")
    @Valid
    private ProductDTO productDTO;

}

