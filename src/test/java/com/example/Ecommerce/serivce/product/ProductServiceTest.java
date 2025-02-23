package com.example.Ecommerce.serivce.product;

import com.example.Ecommerce.DummyObjects;
import com.example.Ecommerce.exceptions.category.CategoryNotFoundException;
import com.example.Ecommerce.exceptions.product.ProductAlreadyExistsException;
import com.example.Ecommerce.exceptions.product.ProductNotFoundException;
import com.example.Ecommerce.mapper.ICategoryMapper;
import com.example.Ecommerce.mapper.IProductMapper;
import com.example.Ecommerce.model.dto.CategoryDto;
import com.example.Ecommerce.model.dto.ProductDto;
import com.example.Ecommerce.model.entity.Category;
import com.example.Ecommerce.model.entity.Product;
import com.example.Ecommerce.repository.CategoryRepository;
import com.example.Ecommerce.repository.ProductRepository;
import com.example.Ecommerce.request.category.AddCategoryRequest;
import com.example.Ecommerce.request.product.AddProductRequest;
import com.example.Ecommerce.request.product.UpdateProductRequest;
import com.example.Ecommerce.serivce.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ICategoryMapper categoryMapper;

    @Mock
    private IProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Category category;
    private AddProductRequest addProductRequest;
    private UpdateProductRequest updateProductRequest;

    @BeforeEach
    void setUp() {
        DummyObjects dummyObjects = new DummyObjects(productMapper, categoryMapper);
        product = DummyObjects.product1;
        category= DummyObjects.category1;
        addProductRequest=DummyObjects.addProductRequest1;
        when(productRepository.findByName(product.getName())).thenReturn(List.of(product));
        when(categoryRepository.findByName(category.getName())).thenReturn(category);
        when(productMapper.toEntityFromAddRequest(addProductRequest)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
    }

    @Test
    void addProduct_ShouldAddProduct_WhenCategoryExists() {
        Product result = productService.addProduct(addProductRequest);
        assertNotNull(result);
        checkEqualityTwoProduct(product,result);
    }

    void checkEqualityTwoProduct(Product product,Product result) {
        assertEquals(product.getName(),result.getName());
        assertEquals(product.getBrand(),result.getBrand());
        assertEquals(product.getDescription(),result.getDescription());
        assertEquals(product.getPrice(),result.getPrice());
        assertEquals(product.getQuantity(),result.getQuantity());
        assertEquals(product.getCategory().getName(),result.getCategory().getName());
    }


}
