package com.example.ecommerce.serivce.product;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.exceptions.category.CategoryNotFoundException;
import com.example.ecommerce.exceptions.product.ProductAlreadyExistsException;
import com.example.ecommerce.exceptions.product.ProductNotFoundException;
import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.mapper.ProductMapper;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.request.product.AddProductRequest;
import com.example.ecommerce.request.product.UpdateProductRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static com.example.ecommerce.constants.ErrorMessages.ProductError.*;
import static com.example.ecommerce.constants.ErrorMessages.ProductError.PRICE_NULL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class
ProductManagementServiceTest {


    private Validator validator = (Validator) Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDto;
    private Category category;
    private CategoryDTO categoryDto;
    private AddProductRequest addProductRequest;
    private UpdateProductRequest updateProductRequest;

    @BeforeEach
    void setUp() {

    }

    @Test
    void addProduct_ReturnsProduct_WhenAddProductRequestIsValid() {
        //Arrange
        when(productRepository.existsByName(product.getName())).thenReturn(false);
        when(categoryRepository.findByName(category.getName())).thenReturn(category);
        when(productMapper.toEntity(addProductRequest)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        //Act
        ProductDTO result = productService.addProduct(addProductRequest);
        //Assert
        assertNotNull(result);
        assertEquals(productDto, result);
        checkEqualityTwoProduct(productDto,result);
        verify(productRepository, times(1)).existsByName(addProductRequest.getName());
        verify(categoryRepository, times(1)).findByName(addProductRequest.getCategory());
        verify(productMapper, times(1)).toEntity(addProductRequest);
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
        ProductDTO updatedProduct=productDto;
      //  updatedProduct.setName(updateProductRequest.getName());

        //Arrange
        when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(categoryRepository.findByName(product.getCategory().getName())).thenReturn(category);
        when(productMapper.toEntity(updateProductRequest)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(updatedProduct);
        //Act
        ProductDTO result =productService.updateProduct(updateProductRequest,product.getId());
        //Assert
        assertNotNull(result);
        assertEquals(updateProductRequest.getName(), result.name());
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
        when(productMapper.toEntity(updateProductRequest)).thenReturn(product);
        when(categoryRepository.findByName(product.getCategory().getName())).thenReturn(null);
        //Act&Assert
        assertThrows(CategoryNotFoundException.class,()->{
            productService.updateProduct(updateProductRequest,product.getId());
        });
        verify(productRepository,times(1)).findById(product.getId());
        verify(categoryRepository,times(1)).findByName(product.getCategory().getName());
    }

    void checkEqualityTwoProduct(ProductDTO product, ProductDTO result) {
        assertEquals(product.name(),result.name());
        assertEquals(product.brand(),result.brand());
        assertEquals(product.description(),result.description());
        assertEquals(product.price(),result.price());
    }
    @Test
   // @RepeatedTest(10)
    void deleteProduct_ReturnsProductNotFoundException_WhenIdNotFound() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,()->{
            productService.deleteProduct(product.getId());
        });
        verify(productRepository,times(1)).findById(product.getId());

    }


}