package com.example.Ecommerce.serivce.category;

import com.example.Ecommerce.model.dto.CategoryDto;
import com.example.Ecommerce.request.category.AddCategoryRequest;
import com.example.Ecommerce.request.category.UpdateCategoryRequest;

public interface ICategoryManagementService {
    CategoryDto addCategory(AddCategoryRequest category);
    CategoryDto updateCategory(UpdateCategoryRequest category, Long id);
    void deleteCategory(Long id);
}
