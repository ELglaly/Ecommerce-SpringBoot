package com.example.ecommerce.serivce.category;

import com.example.ecommerce.exceptions.category.CategoryNotFoundException;
import com.example.ecommerce.exceptions.category.CategoryAlreadyExistsException;
import com.example.ecommerce.mapper.CategoryMapper;
import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.request.category.AddCategoryRequest;
import com.example.ecommerce.request.category.UpdateCategoryRequest;
import com.example.ecommerce.util.CategoryUtils;
import com.example.ecommerce.validator.Category.CategoryValidator;
import groovy.util.logging.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@lombok.extern.slf4j.Slf4j
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryValidator categoryValidator;


    // Constructor to inject CategoryRepository dependency
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CategoryValidator categoryValidator) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.categoryValidator = categoryValidator;
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return The CategoryDTO object representing the retrieved category.
     * @throws CategoryNotFoundException If the category with the specified ID is not found.
     */
    @Transactional(readOnly = true)
    @Override
    public CategoryDTO getCategoryById(Long id) {
        return Optional.ofNullable(categoryRepository.findById(id, CategoryDTO.class))
                .orElseThrow(() -> new CategoryNotFoundException("/CategoryService/getCategoryById",CategoryUtils.CATEGORY_NOT_FOUND+ id));
    }

    /**
     * Retrieves all categories.
     *
     * @return A list of CategoryDTO objects representing all categories.
     */
    @Transactional(readOnly = true)
    @Override
    public Page<CategoryDTO> getAllCategories() {
        // return all categories, map each to DTO, and return as a list
        return categoryRepository.findBy(CategoryDTO.class, Pageable.ofSize(10));
    }


    /**
     * Retrieves a category by its name.
     *
     * @param categoryName The name of the category to retrieve.
     * @return The CategoryDTO object representing the retrieved category.
     * @throws CategoryNotFoundException If the category with the specified name is not found.
     */
    @Transactional(readOnly = true)
    @Override
    public Page<CategoryDTO> searchByName(String categoryName) {
        // return a category by name and map it to DTO
        Pageable pageable = Pageable.ofSize(10);
        Page<Category>  categories = categoryRepository.findByNameContainingOrderByNameAsc(categoryName, pageable, Category.class);
        return  categories.map(categoryMapper::toDto);
    }

    /**
     * Adds a new category.
     *
     * @param request The AddCategoryRequest object containing the details of the category to be added.
     * @return The CategoryDTO object representing the newly added category.
     * @throws CategoryAlreadyExistsException If a category with the same name already exists.
     */

    @Transactional(
            rollbackFor = {CategoryAlreadyExistsException.class}
    )
    @Override
    public CategoryDTO addCategory(AddCategoryRequest request) {
        // Check if the category exists by name. If it does, throw exception.
        categoryValidator.validateAddCategoryRequest(request);
        log.info("Creating category with name: {}", request.name());
        return createCategory(request); // otherwise, create a new category
    }
    /**
     * Helper method to create and save a new category.
     *
     * @param request The AddCategoryRequest object containing the details of the category to be created.
     * @return The CategoryDTO object representing the newly created category.
     */
    private CategoryDTO createCategory(AddCategoryRequest request) {
        log.debug("Mapped Category entity: {}", request);
        Category category =  categoryMapper.toEntity(request);
        category.setCreatedAt(java.time.LocalDateTime.now());
        category.setCreatedBy(getCurrentUsername());
        log.debug("Saving Category entity: {}",category);
        Category savedCategory = categoryRepository.save(category);
        log.info("Category created with ID: {}", savedCategory.getId());
        log.debug("Mapped Category DTO: {}", savedCategory);
        return categoryMapper.toDto(savedCategory);
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {

            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails userDetails) {
                return userDetails.getUsername();
            } else {
                return principal.toString();
            }
        }

        return "system";
    }

    /**
     * Updates an existing category with the provided details.
     *
     * @param request The UpdateCategoryRequest object containing the updated details of the category.
     * @param id The ID of the category to be updated.
     * @return The CategoryDTO object representing the updated category.
     * @throws CategoryNotFoundException If the category with the specified ID is not found.
     */
    @Transactional
    @Override
    public CategoryDTO updateCategory(UpdateCategoryRequest request, Long id) {
        // find an existing category by id. if found, update it. otherwise, throw exception.
        return categoryRepository.findById(id)
                .map(existingCategory ->
                        {
                            log.info("Updating Category with ID: {}", id);
                           return updateExistingCategory(existingCategory, request);
                        }
                )
                .orElseThrow(() ->
                        {
                            log.error("Category with ID: {} not found for update", id);
                            return new CategoryNotFoundException("/CategoryService/updateCategory",CategoryUtils.CATEGORY_NOT_FOUND + id);
                        }
                );
    }

    /**
     * Helper method to update an existing category's details.
     *
     * @param existingCategory The existing Category object to be updated.
     * @param request The UpdateCategoryRequest object containing the updated details of the category.
     * @return The CategoryDTO object representing the updated category.
     */
    private CategoryDTO updateExistingCategory(Category existingCategory, UpdateCategoryRequest request) {

            categoryValidator.validateUpdateCategoryRequest(request);
            if(categoryRepository.existsByNameAndIdNot(request.name(), existingCategory.getId())) {
                log.error("Category with name: {} already exists", request.name());
                throw new CategoryAlreadyExistsException();
            }

            log.debug("Mapped Category entity for update: {}", request);
            Category savedCategory = categoryMapper.toEntity(request);

            log.debug("Updating Category entity: {}", savedCategory);
            savedCategory.setId(existingCategory.getId());

            savedCategory.setCreatedAt(existingCategory.getCreatedAt());
            savedCategory.setCreatedBy(existingCategory.getCreatedBy());
            savedCategory.setUpdatedAt(java.time.LocalDateTime.now());
            savedCategory.setUpdatedBy(getCurrentUsername());

            log.debug("Saving updated Category entity: {}", savedCategory);
            categoryRepository.save(savedCategory);
            log.info("Category updated with ID: {}", savedCategory.getId());

            log.debug("Mapped updated Category DTO: {}", savedCategory);
            return categoryMapper.toDto(savedCategory);// Return the updated category DTO
    }


    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to be deleted.
     * If the category is not found, a CategoryNotFoundException is thrown.
     */
    @Override
    public void deleteCategory(Long id) {

        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            log.error("Category with ID: {} not found for deletion", id);
            throw new CategoryNotFoundException("Category/deleteCategory",CategoryUtils.CATEGORY_NOT_FOUND + id);
        }
    }

    /**
     * Retrieves the total count of categories.
     *
     * @return The total count of categories.
     */
    @Override
    public Long getCategoryCount() {
        // Return the total count of categories
        return categoryRepository.count();
    }

}
