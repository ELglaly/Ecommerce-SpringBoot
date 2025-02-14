package com.example.demo.validator;

import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.request.product.AddProductRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;


@Component
public class ProductValidator implements IProductValidator{

    private final ProductRepository productRepository;

    public ProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateProductDoesNotExist(String name) {
        if (productRepository.existsByName(name)) {
            throw new ResourceAlreadyExistsException("Product Already Exists", "Product");
        }
    }

}
