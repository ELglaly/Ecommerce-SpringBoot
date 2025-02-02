package com.example.demo.serivce.category;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.request.category.AddCategoryRequest;
import com.example.demo.request.category.UpdateCategoryRequest;

public interface ICategoryManagementService {
    CategoryDto addCategory(AddCategoryRequest category);
    CategoryDto updateCategory(UpdateCategoryRequest category, Long id);
    void deleteCategory(Long id);
}
