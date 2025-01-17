package com.example.demo.serivce.category;

import com.example.demo.exceptions.CategoryAlradyExistsException;
import com.example.demo.exceptions.CategoryNotFoundException;
import com.example.demo.model.entity.Category;
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
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                ()->   new CategoryNotFoundException("Category not found")
        );
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    @Override
    public Category addCategory(AddCategoryRequest request) {
        return Optional.of(request).filter(req-> !categoryRepository.existsByName(req.getName()))
                .map(this::createCategory)
                .map(categoryRepository :: save)
                .orElseThrow(() -> new CategoryAlradyExistsException("Category Already Exists"));
    }

    private Category createCategory(AddCategoryRequest request) {
        return new Category(request.getName(),request.getDescription());
    }


    @Override
    public Category updateCategory(UpdateCategoryRequest request, Long id) {
        return categoryRepository.findById(id).map(
                existingCategory -> {
                Category category =updateexisitngCategory (existingCategory, request);
                return categoryRepository.save(category);
                }
        ).orElseThrow(()->   new CategoryNotFoundException("Category not found"));
    }

    public Category updateexisitngCategory(Category existingCategory, UpdateCategoryRequest request) {
        existingCategory.setName(request.getName());
        existingCategory.setDescription(request.getDescription());
        return existingCategory;
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
