package com.example.demo.serivce.category;

import com.example.demo.exceptions.CategoryAlradyExistsException;
import com.example.demo.exceptions.CategoryNotFoundException;
import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.entity.Category;
import com.example.demo.model.mapping.CategoryMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.request.AddCategoryRequest;
import com.example.demo.request.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper::toDto)
                .orElseThrow(()->   new CategoryNotFoundException("Category not found")
        );
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getCategoryByName(String categoryName) {
        return CategoryMapper.toDto(categoryRepository.findByName(categoryName));
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return Optional.of(categoryDto).filter(req-> !categoryRepository.existsByName(req.getName()))
                .map(this::createCategory)
                .orElseThrow(() -> new CategoryAlradyExistsException("Category Already Exists"));
    }

    private CategoryDto createCategory(CategoryDto categoryDto) {
        categoryRepository.save(CategoryMapper.toEntity(categoryDto));
        return categoryDto;
    }


    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        return categoryRepository.findById(id).map(
                existingCategory -> updateExisitngCategory (existingCategory, categoryDto)
        ).orElseThrow(()->   new CategoryNotFoundException("Category not found"));
    }

    public CategoryDto updateExisitngCategory(Category existingCategory, CategoryDto categoryDto) {
        existingCategory.setName(categoryDto.getName());
        existingCategory.setDescription(categoryDto.getDescription());
        categoryRepository.save(existingCategory);
        return categoryDto;
    }

    @Override
    public void deleteCategory(Long id) {
         categoryRepository.findById(id)
                 .ifPresentOrElse(categoryRepository::delete,
                  ()-> {throw new CategoryNotFoundException("Category not found");}
                 );
    }

    @Override
    public Long getCategoryCount() {
        return categoryRepository.count();
    }
}
