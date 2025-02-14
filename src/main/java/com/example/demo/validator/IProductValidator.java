package com.example.demo.validator;

import com.example.demo.model.entity.Product;
import com.example.demo.request.product.AddProductRequest;

import java.util.List;

public interface IProductValidator {
    void validateProductDoesNotExist(String name);
}
