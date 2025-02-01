package com.example.demo.serivce.product;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.request.product.AddProductRequest;
import com.example.demo.request.product.UpdateProductRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
                          ModelMapper modelMapper, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    // Add a new product to the repository
    @Override
    public ProductDto addProduct(AddProductRequest request) {
        // Find product by name; if not found, throw exception
        if(productRepository.existsByName(request.getName()))
        {
            throw new ResourceAlreadyExistsException("Product Already Exists", "Product");
        }
        else
        {
           return createProduct(request);
       }
    }

    // Helper method to create a new Product entity from the productDto and category
    private ProductDto createProduct(AddProductRequest request) {
        Product product = modelMapper.map(request,Product.class);
        productRepository.save(product);
        return modelMapper.map(request,ProductDto.class);
    }

    // return a product by ID and throws exception if not found
    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(product ->  modelMapper.map(product,ProductDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found","Product"));
    }

    // return all products from the repository
    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToProductDto)  // Mapping Product to ProductDto
                .toList();

    }
    // Update an existing product identified by its ID
    @Override
    public ProductDto updateProduct(UpdateProductRequest request, Long id) {
        return productRepository.findById(id)
                .map(product -> updateExistingProduct(product, request))
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found","Product"));
    }

    // Helper method to update product fields from the productDto
    private ProductDto updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setQuantity(request.getQuantity());
        existingProduct.setBrand(request.getBrand());

        // Find and update category if present
        return Optional.ofNullable(categoryRepository.findByName(request.getName()))
                .map(category -> {
                    existingProduct.setCategory(category);
                    productRepository.save(existingProduct);
                    return convertToProductDto(existingProduct);
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
        return productRepository.findByNameContaining(name).stream()
                .map(this::convertToProductDto)
                .toList();
    }

    // return all products under a specific category
    @Override
    public List<ProductDto> getAllProductsByCategory(String category) {
        return productRepository.findByCategoryName(category).stream()
                .map(this::convertToProductDto)
                .toList();
    }

    // return all products of a specific brand
    @Override
    public List<ProductDto> getAllProductsByBrand(String brand) {
        return productRepository.findByBrand(brand)
                .stream() .map(this::convertToProductDto)
                .toList();
    }

    // return all products matching a specific category and brand
    @Override
    public List<ProductDto> getAllProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand)
                .stream() .map(this::convertToProductDto)
                .toList();
    }

    // return all products matching a specific name and category
    @Override
    public List<ProductDto> getAllProductsByNameAndCategory(String name, String category) {
        return productRepository.findByNameAndCategoryName(name, category)
                .stream() .map(this::convertToProductDto)
                .toList();
    }

    // return all products matching a specific brand and name
    @Override
    public List<ProductDto> getAllProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name).stream()
                .map(this::convertToProductDto)
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

    public ProductDto convertToProductDto(Product product) {
        ProductDto productDto = modelMapper.map(product,ProductDto.class);
        List<ImageDto> imageDto = imageRepository.getImageByProductId(product.getId())
                .stream().map(image -> modelMapper.map(image,ImageDto.class))
                .toList();
        productDto.setImageDto(imageDto);
        return productDto;
    }
}
