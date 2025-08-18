package com.example.ecommerce.request.category;

import com.example.ecommerce.request.image.UpdateImageRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
    @Data
    @NoArgsConstructor
@Builder
    public class UpdateCategoryRequest {

        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        @Pattern(regexp = "^[^\\d]*$", message = "Name must not contain digits")
        private String name;

        @NotNull(message = "Description cannot be null")
        @NotBlank(message = "Description cannot be Empty")
        @Size(min = 5, max = 500, message = "Description must be between 5 and 500 characters")
        private String description;


        private List<UpdateImageRequest> images;

    }
