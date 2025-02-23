package com.example.Ecommerce.serivce.product;

import com.example.Ecommerce.DummyObjects;
import com.example.Ecommerce.constants.ErrorMessages;
import com.example.Ecommerce.exceptions.category.CategoryNotFoundException;
import com.example.Ecommerce.exceptions.product.ProductAlreadyExistsException;
import com.example.Ecommerce.exceptions.product.ProductNotFoundException;
import com.example.Ecommerce.mapper.ICategoryMapper;
import com.example.Ecommerce.mapper.IProductMapper;
import com.example.Ecommerce.model.entity.Category;
import com.example.Ecommerce.model.entity.Product;
import com.example.Ecommerce.repository.CategoryRepository;
import com.example.Ecommerce.repository.ProductRepository;
import com.example.Ecommerce.request.product.AddProductRequest;
import com.example.Ecommerce.request.product.UpdateProductRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.Ecommerce.constants.ErrorMessages.ProductError.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductServiceTest {


    private Validator validator = (Validator) Validation.buildDefaultValidatorFactory().getValidator();

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
        product.setId(1L);
        category= DummyObjects.category1;
        addProductRequest=DummyObjects.addProductRequest1;

    }

    @Test
    void addProduct_ReturnsProduct_WhenAddProductRequestIsValid() {
        when(productRepository.existsByName(product.getName())).thenReturn(false);
        when(categoryRepository.findByName(category.getName())).thenReturn(category);
        when(productMapper.toEntityFromAddRequest(addProductRequest)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product result = productService.addProduct(addProductRequest);
        assertNotNull(result);
        checkEqualityTwoProduct(product,result);
        verify(productRepository, times(1)).existsByName(addProductRequest.getName());
        verify(categoryRepository, times(1)).findByName(addProductRequest.getCategory());
        verify(productMapper, times(1)).toEntityFromAddRequest(addProductRequest);
        verify(productRepository, times(1)).save(product);
    }
    @Test
    void addProduct_ReturnsAlreadyExistException_WhenProductExists () {

        when(productRepository.existsByName(addProductRequest.getName())).thenReturn(true);

        assertThrows(ProductAlreadyExistsException.class,()->{
            Product result = productService.addProduct(addProductRequest);
        });

        verify(productRepository, times(1)).existsByName(addProductRequest.getName());
    }

    @Test
    void addProduct_ReturnsNotFoundExistException_WhenCategoryDoesNotExist() {

        when(productRepository.existsByName(addProductRequest.getName())).thenReturn(false);
        when(categoryRepository.findByName(addProductRequest.getCategory())).thenReturn(null);

        assertThrows(CategoryNotFoundException.class,()->{
            Product result = productService.addProduct(addProductRequest);
        });

        verify(productRepository, times(1)).existsByName(addProductRequest.getName());
        verify(categoryRepository, times(1)).findByName(addProductRequest.getCategory());
    }

    @Test
    void addProduct_ReturnsErrorMessage_WhenAnyRequiredFiledIsEmptyInAddProductRequest()
    {
        AddProductRequest addProductRequest1 =  AddProductRequest.builder()
                .name("").description("")
                .brand("").quantity(0).build();
        Set<ConstraintViolation<AddProductRequest>> violations = validator.validate(addProductRequest1);

        assertEquals(violations.size(),6);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        NAME_SIZE,
                        BRAND_SIZE,
                        DESCRIPTION_SIZE,
                        QUANTITY_MIN,
                        CATEGORY_NULL,
                        PRICE_NULL
                );


    }

    @Test
    void addProduct_ReturnsErrorMessage_WhenAnyRequiredFiledIsNullInAddProductRequest()
    {
        AddProductRequest addProductRequest1 =  AddProductRequest.builder()
                .build();
        Set<ConstraintViolation<AddProductRequest>> violations = validator.validate(addProductRequest1);

        assertEquals(violations.size(),6);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        NAME_NULL,
                        BRAND_NULL,
                        DESCRIPTION_NULL,
                        QUANTITY_MIN,
                        CATEGORY_NULL,
                        PRICE_NULL
                );


    }

    @Test
    void getProductById_ReturnsProduct_WhenProductExists()
    {
        when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));

        Product result = productService.getProductById(product.getId());

        assertNotNull(result);

        checkEqualityTwoProduct(product,result);

        verify(productRepository,times(1)).findById(product.getId());
    }
    @Test
    void getProductById_ReturnsNotFoundException_WhenProductDoesNotExist()
    {
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,()-> {
            Product result = productService.getProductById(product.getId());
        });

        verify(productRepository,times(1)).findById(product.getId());
    }

    @Test
    void getAllProducts_ReturnsListOfProducts_WhenProductsExists()
    {
        when(productRepository.findAll()).thenReturn(List.of(product,DummyObjects.product2,DummyObjects.product3));

        List<Product> results = productService.getAllProducts();
        assertNotNull(results);

        checkEqualityTwoProduct(results.get(0),product);
        checkEqualityTwoProduct(results.get(1),DummyObjects.product2);
        checkEqualityTwoProduct(results.get(2),DummyObjects.product3);

        verify(productRepository,times(1)).findAll();
    }
    @Test
    void updateProduct_ReturnsUpdatedProduct_WhenUpdatesAreValid()
    {
        UpdateProductRequest updateProductRequest1 =  UpdateProductRequest.builder()
                .price(product.getPrice())
                .name("ALi")
                .brand("Sherif")
                .description(product.getDescription())
                .category(product.getCategory().getName())
                .quantity(product.getQuantity())
                .build();

        when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(categoryRepository.findByName(product.getCategory().getName())).thenReturn(category);
        when(productMapper.toEntityFromUpdateRequest(updateProductRequest1)).thenReturn(product);

        Product updatedProduct =productService.updateProduct(updateProductRequest1,product.getId());

        assertNotNull(updatedProduct);
        checkEqualityTwoProduct(updatedProduct,product);

        verify(productRepository,times(1)).findById(product.getId());
        verify(categoryRepository,times(1)).findByName(product.getCategory().getName());
        verify(categoryRepository,times(1)).findByName(product.getCategory().getName());
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
