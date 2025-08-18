package com.example.ecommerce.serivce.category;

import com.example.ecommerce.dto.CategoryDTO;

import java.util.List;

public interface ICategorySearchService {
    CategoryDTO getCategoryById(Long id);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryByName(String categoryName);

}
