package com.example.ecommerce.serivce.category;

import com.example.ecommerce.dto.CategoryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategorySearchService {
    CategoryDTO getCategoryById(Long id);
    Page<CategoryDTO> getAllCategories();
    Page<CategoryDTO> searchByName(String categoryName);

}
