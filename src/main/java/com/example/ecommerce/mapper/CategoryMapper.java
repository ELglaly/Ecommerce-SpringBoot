package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.request.category.AddCategoryRequest;
import com.example.ecommerce.request.category.UpdateCategoryRequest;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between Category entities and CategoryDTOs.
 * It also handles conversion from AddCategoryRequest and UpdateCategoryRequest to CategoryDTOs and Category entities.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDto(Category category);

    Category toEntity(CategoryDTO categoryDTO);

    CategoryDTO toDto(AddCategoryRequest addCategoryRequest);

    Category toEntity(AddCategoryRequest addCategoryRequest);

    CategoryDTO toDto(UpdateCategoryRequest updateCategoryRequest);

    Category toEntity(UpdateCategoryRequest updateCategoryRequest);
}
