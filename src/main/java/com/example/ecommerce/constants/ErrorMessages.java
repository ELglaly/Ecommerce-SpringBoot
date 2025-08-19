package com.example.ecommerce.constants;

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

        public static class UserError {
                public static final String USERNAME_NULL = "Username cannot be null";
                public static final String USERNAME_EMPTY = "Username cannot be empty";
                public static final String USERNAME_SIZE = "Username must be between 3 and 50 characters";
                public static final String USERNAME_PATTERN = "Username can only contain letters, numbers, dots, underscores, and hyphens";

                public static final String PASSWORD_NULL = "Password cannot be null";
                public static final String PASSWORD_PATTERN = "Password must be at least 6 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character (@#$%^&+=!)";

                public static final String EMAIL_NULL = "Email cannot be null";
                public static final String EMAIL_INVALID = "Email must be a valid email address";

                public static final String PHONE_NUMBER_NULL = "Phone number cannot be null";
                public static final String PHONE_NUMBER_INVALID = "Phone number must be a valid phone number format";

                public static final String ADDRESS_NULL = "Address cannot be null";
        }
    }
