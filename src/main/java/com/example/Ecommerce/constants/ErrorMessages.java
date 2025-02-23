package com.example.Ecommerce.constants;

public class ErrorMessages {
        public static final String RESOURCE_NOT_FOUND = "Resource not found.";
        public static final String INVALID_INPUT = "Invalid input provided.";
        public static final String RESOURCE_ALREADY_EXISTS = "Resource already exists.";
        public static final String UNAUTHORIZED_ACCESS = "Unauthorized access.";

        public static class ProductError{
                public static final String NAME_NULL = "Name cannot be null";
                public static final String NAME_EMPTY = "Name cannot be empty";
                public static final String NAME_SIZE = "Name must be between 2 and 100 characters";

                public static final String BRAND_NULL = "Brand cannot be null";
                public static final String BRAND_EMPTY = "Brand cannot be empty";
                public static final String BRAND_SIZE = "Brand must be between 2 and 50 characters";

                public static final String DESCRIPTION_NULL = "Description cannot be null";
                public static final String DESCRIPTION_EMPTY = "Description cannot be Empty";
                public static final String DESCRIPTION_SIZE = "Description must be between 5 and 500 characters";

                public static final String PRICE_NULL = "Price cannot be null";
                public static final String PRICE_MIN = "Price must be greater than 0";
                public static final String PRICE_DIGITS = "Price must have up to 2 decimal places";

                public static final String QUANTITY_MIN = "Quantity must be at least 1";
                public static final String QUANTITY_MAX = "Quantity cannot exceed 10,000";

                public static final String CATEGORY_NULL = "Category cannot be null";
        }
    }
