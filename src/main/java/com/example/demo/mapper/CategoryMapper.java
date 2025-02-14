package com.example.demo.mapper;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Category;

import com.example.demo.model.entity.Image;
import com.example.demo.request.category.AddCategoryRequest;
import com.example.demo.request.category.UpdateCategoryRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class CategoryMapper implements ICategoryMapper {
    private final ModelMapper modelMapper;
    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

    }

    @Override
    public Category toEntityFromAddRequest(AddCategoryRequest addRequest) {
        Category category = modelMapper.map(addRequest, Category.class);
        List<Image> images = addRequest.getImages().stream()
                .map(image -> modelMapper.map(image, Image.class))
                .toList();
        category.setImages(images);
        return category;
    }

    @Override
    public Category toEntityFromDto(CategoryDto dto) {
        Category category = modelMapper.map(dto, Category.class);
        List<Image> images = Optional.ofNullable(dto.getImagesDto())
                .orElse(Collections.emptyList())
                .stream()
                .map(image -> modelMapper.map(image, Image.class))
                .toList();
        category.setImages(images);
        return category;
    }

    @Override
    public CategoryDto toDto(Category entity) {
        CategoryDto dto = modelMapper.map(entity, CategoryDto.class);
        List<ProductDto> products = Optional.ofNullable(entity.getProducts())
                .orElse(Collections.emptyList())
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
        List<ImageDto> imageDtos =Optional.ofNullable( entity.getImages())
                .orElse(Collections.emptyList())
                .stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        dto.setProductsDto(products);
        dto.setImagesDto(imageDtos);
        return dto;
    }

    @Override
    public Category toEntityFromUpdateRequest(UpdateCategoryRequest updateRequest) {
        Category category = modelMapper.map(updateRequest, Category.class);
        List<Image> images =Optional.ofNullable( updateRequest.getImages())
                .orElse(Collections.emptyList())
                .stream()
                .map(image -> modelMapper.map(image, Image.class))
                .toList();
        category.setImages(images);
        return category;
    }
}
