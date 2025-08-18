package com.example.ecommerce.serivce.category;

import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.request.category.AddCategoryRequest;
import com.example.ecommerce.request.category.UpdateCategoryRequest;

public interface ICategoryManagementService {
    CategoryDTO addCategory(AddCategoryRequest category);
    CategoryDTO updateCategory(UpdateCategoryRequest category, Long id);
    void deleteCategory(Long id);

}
