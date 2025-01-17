package com.example.demo.serivce.product;

import com.example.demo.exceptions.CategoryNotFoundException;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.request.AddProductRequest;
import com.example.demo.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // Add a new product to the repository
    @Override
    public Product addProduct(AddProductRequest request) {
        // Find category by name; if not found, throw exception
        Category category = categoryRepository.findByName(request.getName());
        if (category == null) {
            throw new CategoryNotFoundException("Category Not Found!");
        } else {
            // Create and save the new product
            return productRepository.save(createProduct(request, category));
        }
    }

    // Helper method to create a new Product entity from the request and category
    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getQuantity(),
                category,
                request.getBrand()
        );
    }

    // return a product by ID and throws exception if not found
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }

    // return all products from the repository
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Update an existing product identified by its ID
    @Override
    public Product updateProduct(UpdateProductRequest request, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    // Update product fields and save
                    Product updatedProduct = updateExistingProduct(existingProduct, request);
                    return productRepository.save(updatedProduct); // Save and return updated product
                })
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }

    // Helper method to update product fields from the request
    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setQuantity(request.getQuantity());
        existingProduct.setBrand(request.getBrand());

        // Find and update category if present
        Category category = categoryRepository.findByName(request.getName());
        if (category != null) {
            existingProduct.setCategory(category);
            productRepository.save(existingProduct);
        } else {
            throw new CategoryNotFoundException("Category Not Found");
        }
        return existingProduct;
    }

    // Delete a product by ID, throws exception if not found
    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(
                productRepository::delete, // Delete product if found
                () -> { throw new ProductNotFoundException("Product Not Found"); } // Throw exception if not found
        );
    }

    // return products by their name
    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    // return all products under a specific category
    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    // return all products of a specific brand
    @Override
    public List<Product> getAllProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    // return all products matching a specific category and brand
    @Override
    public List<Product> getAllProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    // return all products matching a specific name and category
    @Override
    public List<Product> getAllProductsByNameAndCategory(String name, String category) {
        return productRepository.findByNameAndCategoryName(name, category);
    }

    // return all products matching a specific brand and name
    @Override
    public List<Product> getAllProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    // Return the count of products for a specific category
    @Override
    public int getProductsCountByCategory(String category) {
        return productRepository.countByCategoryName(category);
    }

    // Return the count of products under a specific brand
    @Override
    public int getProductsCountByBrand(String brand) {
        return productRepository.countByBrand(brand);
    }

    // Return the count of products with a specific name
    @Override
    public int getProductsCountByName(String name) {
        return productRepository.countByName(name);
    }
}
