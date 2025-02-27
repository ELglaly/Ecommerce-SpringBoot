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
import com.example.Ecommerce.request.product.AddProductRequest;
import com.example.Ecommerce.request.product.UpdateProductRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.Ecommerce.constants.ErrorMessages.ProductError.*;
import static com.example.Ecommerce.constants.ErrorMessages.ProductError.PRICE_NULL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProductManagementServiceTest {


    private Validator validator = (Validator) Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private IProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDto productDto;
    private Category category;
    private CategoryDto categoryDto;
    private AddProductRequest addProductRequest;
    private UpdateProductRequest updateProductRequest;

    @BeforeEach
    void setUp() {
        DummyObjects dummyObjects=new DummyObjects();
        product = DummyObjects.product1;
        productDto =dummyObjects.productDto1;
        product.setId(1L);
        category= DummyObjects.category1;
        categoryDto =DummyObjects.categoryDto1;
        addProductRequest=DummyObjects.addProductRequest1;
        updateProductRequest=DummyObjects.updateProductRequest;
    }

    @Test
    void addProduct_ReturnsProduct_WhenAddProductRequestIsValid() {
        //Arrange
        when(productRepository.existsByName(product.getName())).thenReturn(false);
        when(categoryRepository.findByName(category.getName())).thenReturn(category);
        when(productMapper.toEntityFromAddRequest(addProductRequest)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        //Act
        ProductDto result = productService.addProduct(addProductRequest);
        //Assert
        assertNotNull(result);
        assertEquals(productDto, result);
        checkEqualityTwoProduct(productDto,result);
        verify(productRepository, times(1)).existsByName(addProductRequest.getName());
        verify(categoryRepository, times(1)).findByName(addProductRequest.getCategory());
        verify(productMapper, times(1)).toEntityFromAddRequest(addProductRequest);
        verify(productMapper, times(1)).toDto(product);
        verify(productRepository, times(1)).save(product);
    }
    @Test
    void addProduct_ReturnsAlreadyExistException_WhenProductExists () {

        //Arrange
        when(productRepository.existsByName(addProductRequest.getName())).thenReturn(true);

        //Act&Assert
        assertThrows(ProductAlreadyExistsException.class,()->{
              productService.addProduct(addProductRequest);
        });

        verify(productRepository, times(1)).existsByName(addProductRequest.getName());
    }

    @Test
    void addProduct_ReturnsNotFoundExistException_WhenCategoryDoesNotExist() {
        //Arrange
        when(productRepository.existsByName(addProductRequest.getName())).thenReturn(false);
        when(categoryRepository.findByName(addProductRequest.getCategory())).thenReturn(null);

        //Act&Assert
        assertThrows(CategoryNotFoundException.class,()->{
             productService.addProduct(addProductRequest);
        });
        verify(productRepository, times(1)).existsByName(addProductRequest.getName());
        verify(categoryRepository, times(1)).findByName(addProductRequest.getCategory());
    }

    @Test
    void addProduct_ReturnsErrorMessage_WhenAnyRequiredFiledIsEmptyInAddProductRequest()
    {
        //Arrange
        AddProductRequest addProductRequest1 =  AddProductRequest.builder()
                .name("").description("")
                .brand("").quantity(0).build();

        Set<ConstraintViolation<AddProductRequest>> violations = validator.validate(addProductRequest1);
        ////Act&Assert
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
        //Arrange
        AddProductRequest addProductRequest1 =  AddProductRequest.builder()
                .build();
        // Act&Assert
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
    void updateProduct_ReturnsUpdatedProduct_WhenUpdatesAreValid()
    {
        ProductDto updatedProduct=productDto;
        updatedProduct.setName(updateProductRequest.getName());

        //Arrange
        when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(categoryRepository.findByName(product.getCategory().getName())).thenReturn(category);
        when(productMapper.toEntityFromUpdateRequest(updateProductRequest)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(updatedProduct);
        //Act
        ProductDto result =productService.updateProduct(updateProductRequest,product.getId());
        //Assert
        assertNotNull(result);
        assertEquals(updateProductRequest.getName(), result.getName());
        verify(productRepository,times(1)).findById(product.getId());
        verify(categoryRepository,times(1)).findByName(product.getCategory().getName());
        verify(categoryRepository,times(1)).findByName(product.getCategory().getName());
    }
    @Test
    void updateProduct_ReturnsProductNotFoundException_WhenIdNotFound()
    {
        //Arrange
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        //Act&Assert
        assertThrows(ProductNotFoundException.class,()->{
            productService.updateProduct(updateProductRequest,product.getId());
        });
        verify(productRepository,times(1)).findById(product.getId());

    }
    @Test
    void updateProduct_ReturnsCategoryNotFoundException_WhenCategoryNameDoesNotExist()
    {
        //Arrange
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));
        when(productMapper.toEntityFromUpdateRequest(updateProductRequest)).thenReturn(product);
        when(categoryRepository.findByName(product.getCategory().getName())).thenReturn(null);
        //Act&Assert
        assertThrows(CategoryNotFoundException.class,()->{
            productService.updateProduct(updateProductRequest,product.getId());
        });
        verify(productRepository,times(1)).findById(product.getId());
        verify(categoryRepository,times(1)).findByName(product.getCategory().getName());
    }

    void checkEqualityTwoProduct(ProductDto product,ProductDto result) {
        assertEquals(product.getName(),result.getName());
        assertEquals(product.getBrand(),result.getBrand());
        assertEquals(product.getDescription(),result.getDescription());
        assertEquals(product.getPrice(),result.getPrice());
        assertEquals(product.getQuantity(),result.getQuantity());
        assertEquals(product.getCategoryDto().getProductsDto(),result.getCategoryDto().getProductsDto());
    }
    @Test
    void deleteProduct_ReturnsProductNotFoundException_WhenIdNotFound() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()->{
            productService.deleteProduct(product.getId());
        });
        verify(productRepository,times(1)).findById(product.getId());
    }
}