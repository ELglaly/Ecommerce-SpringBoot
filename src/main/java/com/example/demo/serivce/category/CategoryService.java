package com.example.demo.serivce.category;

import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.entity.Category;
import com.example.demo.model.mapping.CategoryMapper;
import com.example.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    // Constructor to inject CategoryRepository dependency
    public CategoryService (CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        // find category by Id and map it to Dto. If not found, throw exception
        return categoryRepository.findById(id)
                .map(CategoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found", "Category"));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        // return all categories, map each to DTO, and return as a list
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getCategoryByName(String categoryName) {
        // return a category by name and map it to DTO
        return CategoryMapper.toDto(categoryRepository.findByName(categoryName));
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        // Check if the category exists by name. If it does, throw exception.
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new ResourceAlreadyExistsException("Category Already Exists", "Category");
        } else {
            return createCategory(categoryDto); // otherwise, create a new category
        }
    }

    // Helper method to create and save a new category
    private CategoryDto createCategory(CategoryDto categoryDto) {
        return CategoryMapper.toDto(categoryRepository.save(CategoryMapper.toEntity(categoryDto)));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        // find an existing category by id. if found, update it. otherwise, throw exception.
        return categoryRepository.findById(id)
                .map(existingCategory -> updateExistingCategory(existingCategory, categoryDto))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found","Category"));
    }

    // Helper method to update an existing category's details
    private CategoryDto updateExistingCategory(Category existingCategory, CategoryDto categoryDto) {
        if(existingCategory.getName().equals(categoryDto.getName())) {
            throw new ResourceAlreadyExistsException("Category Already Exists", "Category");
        }
        else
        {
            existingCategory.setName(categoryDto.getName());
            existingCategory.setDescription(categoryDto.getDescription());
            existingCategory = categoryRepository.save(existingCategory);
        }
        return CategoryMapper.toDto(existingCategory); // Return the updated category DTO
    }

    @Override
    public void deleteCategory(Long id) {
        // Find a category by ID. If found, delete it. Otherwise, throw exception.
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> { throw new ResourceNotFoundException("Category not found","Category"); });
    }

    @Override
    public Long getCategoryCount() {
        // Return the total count of categories
        return categoryRepository.count();
    }
}
