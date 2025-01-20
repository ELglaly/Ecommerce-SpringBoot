package com.example.demo.serivce.product;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Product;
import com.example.demo.model.mapping.ProductMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Add a new product to the repository
    @Override
    public ProductDto addProduct(ProductDto productDto) {
        // Find product by name; if not found, throw exception
        return Optional.of(productDto).filter(req -> !productRepository.existsByName(req.getName()))
                .map(this::createProduct)
                .orElseThrow(()-> new ResourceAlreadyExistsException("Product Already Exists", "Product"));
    }

    // Helper method to create a new Product entity from the productDto and category
    private ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        productRepository.save(product);
        return productDto;
    }

    // return a product by ID and throws exception if not found
    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found","Product"));
    }

    // return all products from the repository
    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream().map(ProductMapper::toDto)
                .toList();
    }

    // Update an existing product identified by its ID
    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        return productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct, productDto))
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found","Product"));
    }

    // Helper method to update product fields from the productDto
    private ProductDto updateExistingProduct(Product existingProduct, ProductDto productDto) {
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setBrand(productDto.getBrand());

        // Find and update category if present
        return Optional.ofNullable(categoryRepository.findByName(productDto.getName()))
                .map(category -> {
                    existingProduct.setCategory(category);
                    productRepository.save(existingProduct);
                    return productDto;
                }).orElseThrow(()->new ResourceNotFoundException("Category Not Found","Category"));
    }

    // Delete a product by ID, throws exception if not found
    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(
                productRepository::delete, // Delete product if found
                () -> { throw new ResourceNotFoundException("Product Not Found","Product"); } // Throw exception if not found
        );
    }

    // return products by their name
    @Override
    public List<ProductDto> getProductsByName(String name) {
        return productRepository.findByName(name)
                .stream().map(ProductMapper::toDto)
                .toList();
    }

    // return all products under a specific category
    @Override
    public List<ProductDto> getAllProductsByCategory(String category) {
        return productRepository.findByCategoryName(category)
                .stream().map(ProductMapper::toDto)
                .toList();
    }

    // return all products of a specific brand
    @Override
    public List<ProductDto> getAllProductsByBrand(String brand) {
        return productRepository.findByBrand(brand)
                .stream().map(ProductMapper::toDto)
                .toList();
    }

    // return all products matching a specific category and brand
    @Override
    public List<ProductDto> getAllProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand)
                .stream().map(ProductMapper::toDto)
                .toList();
    }

    // return all products matching a specific name and category
    @Override
    public List<ProductDto> getAllProductsByNameAndCategory(String name, String category) {
        return productRepository.findByNameAndCategoryName(name, category)
                .stream().map(ProductMapper::toDto)
                .toList();
    }

    // return all products matching a specific brand and name
    @Override
    public List<ProductDto> getAllProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name).stream()
                .map(ProductMapper::toDto)
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
