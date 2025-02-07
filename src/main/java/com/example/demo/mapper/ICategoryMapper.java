package com.example.demo.mapper;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.entity.Category;
import com.example.demo.request.category.AddCategoryRequest;
import com.example.demo.request.category.UpdateCategoryRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;


public interface ICategoryMapper extends
        IDtoToEntityMapper<Category, CategoryDto> ,
        IAddRequestToEntityMapper<Category, AddCategoryRequest>,
        IUpdateRequestToEntityMapper<Category, UpdateCategoryRequest>,
        IEntityToDtoMapper<Category,CategoryDto>
{
}
