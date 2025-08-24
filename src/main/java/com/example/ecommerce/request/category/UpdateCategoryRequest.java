package com.example.ecommerce.request.category;

import com.example.ecommerce.request.image.UpdateImageRequest;
import com.example.ecommerce.util.CategoryUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdateCategoryRequest(

        @NotBlank(message = CategoryUtils.INVALID_CATEGORY_NAME)
        @Size(min = 3, max = 100, message = CategoryUtils.CATEGORY_NAME_SIZE)
        @Pattern(regexp = CategoryUtils.CATEGORY_NAME_PATTERN, message = CategoryUtils.CATEGORY_NAME_PATTERN_MESSAGE)
        String name,

        @NotBlank(message = CategoryUtils.INVALID_CATEGORY_DESCRIPTION)
        @Size(min = 5, max = 500, message = CategoryUtils.CATEGORY_DESCRIPTION_SIZE)
        String description,

        List<UpdateImageRequest> images) {

}
