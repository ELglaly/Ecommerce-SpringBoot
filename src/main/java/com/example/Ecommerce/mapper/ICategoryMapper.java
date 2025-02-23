package com.example.Ecommerce.mapper;

import com.example.Ecommerce.model.dto.CategoryDto;
import com.example.Ecommerce.model.entity.Category;
import com.example.Ecommerce.request.category.AddCategoryRequest;
import com.example.Ecommerce.request.category.UpdateCategoryRequest;


public interface ICategoryMapper extends
        IDtoToEntityMapper<Category, CategoryDto> ,
        IAddRequestToEntityMapper<Category, AddCategoryRequest>,
        IUpdateRequestToEntityMapper<Category, UpdateCategoryRequest>,
        IEntityToDtoMapper<Category,CategoryDto>
{
}
