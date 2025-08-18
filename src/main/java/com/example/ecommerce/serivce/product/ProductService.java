package com.example.ecommerce.serivce.product;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.exceptions.category.CategoryNotFoundException;
import com.example.ecommerce.exceptions.product.ProductAlreadyExistsException;
import com.example.ecommerce.exceptions.product.ProductNotFoundException;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.request.product.AddProductRequest;
import com.example.ecommerce.request.product.UpdateProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    // Add a new product to the repository
    @Override
    public ProductDTO addProduct(AddProductRequest request) {
        Optional.of(productRepository.existsByName(request.getName()))
                .filter(exists -> exists) // Check if the product exists
                .ifPresent(exists -> {
                    throw new ProductAlreadyExistsException("Product already exists");
                });
           Category category= categoryExist(request);
           return createProduct(request,category);
    }

    private Category categoryExist(AddProductRequest request) {
        return Optional.ofNullable(categoryRepository.findByName(request.getCategory()))
                .orElseThrow(()-> new CategoryNotFoundException("Category Not Found"));
    }

    // Helper method to create a new Product entity from the productDto and category
    private ProductDTO createProduct(AddProductRequest request, Category category) {
        Product product = productMapper.toEntityFromAddRequest(request);
        product.setCategory(category);
        product = productRepository.save(product);
       ProductDTO productDto= productMapper.toDto(product);
       return productDto;
    }

    // return a product by ID and throws exception if not found
    @Override
    public ProductDTO getProductDtoById(Long id) {
        return productMapper.toDto(getProductById(id));
    }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }
        // return all products from the repository
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll()
                .stream()// Mapping Product to ProductDTO
                .toList();

    }
    // Update an existing product identified by its ID
    @Override
    public ProductDTO updateProduct(UpdateProductRequest request, Long id) {
        return productRepository.findById(id)
                .map(product -> updateExistingProduct(product, request))
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }

    // Helper method to update product fields from the productDto
    private ProductDTO updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
         Product updatedProduct=productMapper.toEntityFromUpdateRequest(request);
         updatedProduct.setId(existingProduct.getId());
         updatedProduct.setImages(existingProduct.getImages());

        // Find and update category if present
        Category category= Optional.ofNullable(categoryRepository.findByName(request.getCategory()))
                .orElseThrow(()->new CategoryNotFoundException("Category Not Found"));

        updatedProduct.setCategory(category);
        productRepository.save(updatedProduct);
        return productMapper.toDto(updatedProduct);
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
        return productRepository.findByNameContaining(name).stream()
                .toList();
    }

    // return all products under a specific category
    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return productRepository.findByCategoryName(category).stream()
                .toList();
    }

    // return all products of a specific brand
    @Override
    public List<Product> getAllProductsByBrand(String brand) {
        return productRepository.findByBrand(brand)
                .stream()
                .toList();
    }

    // return all products matching a specific category and brand
    @Override
    public List<Product> getAllProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand)
                .stream()
                .toList();
    }

    // return all products matching a specific name and category
    @Override
    public List<Product> getAllProductsByNameAndCategory(String name, String category) {
        return productRepository.findByNameAndCategoryName(name, category)
                .stream()
                .toList();
    }

    // return all products matching a specific brand and name
    @Override
    public List<Product> getAllProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name).stream()
                .toList();
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
