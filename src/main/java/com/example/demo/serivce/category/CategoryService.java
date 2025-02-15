package com.example.demo.serivce.category;

import com.example.demo.exceptions.category.CategoryNotFoundException;
import com.example.demo.exceptions.category.CategoryAlreadyExistsException;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.request.category.AddCategoryRequest;
import com.example.demo.request.category.UpdateCategoryRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final CategoryMapper categoryMapper;

    // Constructor to inject CategoryRepository dependency
    public CategoryService (CategoryRepository categoryRepository, ModelMapper modelMapper, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        // find category by ID and map it to Dto. If not found, throw exception
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        // return all categories, map each to DTO, and return as a list
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getCategoryByName(String categoryName) {
        // return a category by name and map it to DTO
        Optional<Category> category = Optional.ofNullable(categoryRepository.findByName(categoryName));
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Category not found");
        }
        else
        {
            return  categoryMapper.toDto(category.get());
        }

    }
        @Override
    public CategoryDto addCategory(AddCategoryRequest request) {
        // Check if the category exists by name. If it does, throw exception.
        if (categoryRepository.existsByName(request.getName())) {
            throw new CategoryAlreadyExistsException("Category Already Exists");
        } else {
            return createCategory(request); // otherwise, create a new category
        }
    }

    // Helper method to create and save a new category
    private CategoryDto createCategory(AddCategoryRequest request) {
        Category category =  categoryMapper.toEntityFromAddRequest(request);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(UpdateCategoryRequest request, Long id) {
        // find an existing category by id. if found, update it. otherwise, throw exception.
        return categoryRepository.findById(id)
                .map(existingCategory -> updateExistingCategory(existingCategory, request))
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    // Helper method to update an existing category's details
    private CategoryDto updateExistingCategory(Category existingCategory, UpdateCategoryRequest request) {
        if(categoryRepository.countByName(request.getName())>1) {
            throw new CategoryAlreadyExistsException("Category Already Exists");
        }
        else
        {
            Category savedCategory = categoryMapper.toEntityFromUpdateRequest(request);
            savedCategory.setId(existingCategory.getId());
            categoryRepository.save(savedCategory);
            return categoryMapper.toDto(savedCategory);// Return the updated category DTO
        }
    }

    @Override
    public void deleteCategory(Long id) {
        // Find a category by ID. If found, delete it. Otherwise, throw exception.
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,
                        () -> { throw new CategoryNotFoundException("Category not found"); });
    }

    @Override
    public Long getCategoryCount() {
        // Return the total count of categories
        return categoryRepository.count();
    }
}
