package com.example.Ecommerce.serivce.category;

import com.example.Ecommerce.model.dto.CategoryDto;

import java.util.List;

public interface ICategorySearchService {
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryByName(String categoryName);
}
