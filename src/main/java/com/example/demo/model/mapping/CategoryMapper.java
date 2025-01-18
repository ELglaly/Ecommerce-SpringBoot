package com.example.demo.model.mapping;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Category;

import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .description(category.getDescription())
                .name(category.getName())
                .productsDto(category.getProducts().stream()
                        .map(ProductMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Category toEntity(CategoryDto categoryDto) {

        return Category.builder()
                .id(categoryDto.getId())
                .description(categoryDto.getDescription())
                .name(categoryDto.getName())
                .products(categoryDto.getProductsDto().stream()
                        .map(ProductMapper::toEntity)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
