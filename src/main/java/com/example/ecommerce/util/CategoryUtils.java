package com.example.ecommerce.util;

public class CategoryUtils {
    public static final String CATEGORY_NOT_FOUND = "Category not found with id: ";
    public static final String CATEGORY_DELETED = "Category deleted successfully.";
    public static final String CATEGORY_UPDATED = "Category updated successfully.";
    public static final String CATEGORY_ADDED = "Category added successfully.";
    public static final String CATEGORY_ALREADY_EXISTS = "Category with the same name already exists.";
    public static final String INVALID_CATEGORY_NAME = "Category name cannot be null or blank.";
    public static final String INVALID_CATEGORY_DESCRIPTION = "Category description cannot be null or blank.";
    public static final String INVALID_CATEGORY_ID = "Category ID cannot be null.";
    public static final String CATEGORY_FETCHED = "Categories fetched successfully.";
    public static final String CATEGORY_NAME_SIZE = "Category name must be between 3 and 100 characters.";
    public static final String CATEGORY_DESCRIPTION_SIZE = "Category description must be between 5 and 500 characters.";
    public static final String CATEGORY_NAME_PATTERN = "^[^\\d]*$";
    public static final String CATEGORY_NAME_PATTERN_MESSAGE = "Category name must not contain digits.";
}
