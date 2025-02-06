package com.example.demo.mapper;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Category;

import com.example.demo.model.entity.Image;
import com.example.demo.request.category.AddCategoryRequest;
import com.example.demo.request.category.UpdateCategoryRequest;
import org.modelmapper.ModelMapper;

import java.util.List;
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
        List<Image> images = dto.getImagesDto().stream()
                .map(image -> modelMapper.map(image, Image.class))
                .toList();
        category.setImages(images);
        return category;
    }

    @Override
    public CategoryDto toDto(Category entity) {
        CategoryDto dto = modelMapper.map(entity, CategoryDto.class);
        List<ProductDto> products = entity.getProducts().stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
        List<ImageDto> imageDtos = entity.getImages().stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        dto.setProductsDto(products);
        dto.setImagesDto(imageDtos);
        return dto;
    }

    @Override
    public Category toEntityFromUpdateRequest(UpdateCategoryRequest updateRequest) {
        Category category = modelMapper.map(updateRequest, Category.class);
        List<Image> images = updateRequest.getImages().stream()
                .map(image -> modelMapper.map(image, Image.class))
                .toList();
        category.setImages(images);
        return category;
    }
}
