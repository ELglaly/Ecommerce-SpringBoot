package com.example.ecommerce.validator.Category;


import com.example.ecommerce.entity.Category;
import com.example.ecommerce.exceptions.ValidationException;
import com.example.ecommerce.exceptions.category.CategoryAlreadyExistsException;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.request.category.AddCategoryRequest;
import com.example.ecommerce.request.category.UpdateCategoryRequest;
import groovy.util.logging.Slf4j;
import io.micrometer.core.instrument.config.validate.Validated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@lombok.extern.slf4j.Slf4j
@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryValidatorImpl implements CategoryValidator {
    private final CategoryRepository categoryRepository;

    @Override
    public void validateCategoryName(String name) {

        log.info("Validating Category Name: {}", name);
        validateNotNullOrBlank(name,"name cannot be null or blank");
    }

    @Override
    public void validateCategoryDescription(String description) {
        log.info("Validating Category description: {}", description);
        validateNotNullOrBlank(description,"Description cannot be null or blank");
    }


    @Override
    public void validateCategoryId(Long id) {
        if(id==null)
        {
            throw new ValidationException("Category Id cannot be null");
        }
    }

    @Override
    public void validateAddCategoryRequest(AddCategoryRequest request) {
        log.debug("validating add category request");
        validateCategoryName(request.name());
        if (categoryRepository.existsByName(request.name())) {
            throw new CategoryAlreadyExistsException("Category Name Already Exists");
        }
        validateCategoryDescription(request.description());

    }

    @Override
    public void validateUpdateCategoryRequest(UpdateCategoryRequest request) {
        log.debug("validating update category request");
        validateCategoryName(request.name());
        validateCategoryDescription(request.description());
    }
    private void validateNotNullOrBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            log.error("Value is null or blank :{}", message);
            throw new ValidationException(message);
        }
    }
}

