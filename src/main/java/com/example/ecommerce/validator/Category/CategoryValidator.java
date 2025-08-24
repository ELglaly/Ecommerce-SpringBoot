package com.example.ecommerce.validator.Category;

import com.example.ecommerce.request.category.AddCategoryRequest;
import com.example.ecommerce.request.category.UpdateCategoryRequest;

public interface CategoryValidator {
    void validateCategoryName(String name);
    void validateCategoryDescription(String description);
    void validateCategoryId(Long id);
    void validateAddCategoryRequest(AddCategoryRequest request);
    void validateUpdateCategoryRequest(UpdateCategoryRequest request);
}
