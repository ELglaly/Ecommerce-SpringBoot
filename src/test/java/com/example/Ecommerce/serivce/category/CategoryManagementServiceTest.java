package com.example.Ecommerce.serivce.category;

import com.example.Ecommerce.exceptions.category.CategoryAlreadyExistsException;
import com.example.Ecommerce.exceptions.category.CategoryNotFoundException;
import com.example.Ecommerce.mapper.ICategoryMapper;
import com.example.Ecommerce.model.dto.CategoryDto;
import com.example.Ecommerce.model.entity.Category;
import com.example.Ecommerce.repository.CategoryRepository;
import com.example.Ecommerce.request.category.AddCategoryRequest;
import com.example.Ecommerce.request.category.UpdateCategoryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryManagementServiceTest {

    @Mock
    private ICategoryMapper categoryMapper;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryDto categoryDto;
    private AddCategoryRequest addCategoryRequest;
    private UpdateCategoryRequest updateCategoryRequest;
    @BeforeEach
    void setUp() {
        category = new Category.Builder()
                .name("Electronics")
                .description("Category for electronic products.")
                .build();
        categoryDto = CategoryDto.builder()
                .name("Books")
                .description("Category for books and literature.")
                .build();
        updateCategoryRequest = UpdateCategoryRequest.builder()
                .name("Electronics Updated")
                .description("Category for electronic products.")
                .build();
        addCategoryRequest = AddCategoryRequest.builder()
                .name("Electronics")
                .description("Category for electronic products.")
                .build();
    }

    @Test
    void addCategory_ReturnsCategoryDto_WhenCategoryIsValid() {
        // Arrange
        when(categoryMapper.toEntityFromAddRequest(addCategoryRequest)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // Act
        CategoryDto result = categoryService.addCategory(addCategoryRequest);

        // Assert
        assertNotNull(result);
        assertEquals(categoryDto, result);
        verify(categoryMapper, times(1)).toEntityFromAddRequest(addCategoryRequest);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryMapper, times(1)).toDto(category);
    }
    @Test
    void addCategory_ReturnsAlreadyExistsException_WhenCategoryNameExists() {
        // Arrange
         when(categoryRepository.existsByName(addCategoryRequest.getName())).thenReturn(true);

        // Act & Assert
        assertThrows(CategoryAlreadyExistsException.class, () -> {
            CategoryDto result = categoryService.addCategory(addCategoryRequest);
        });
    }
    @Test
    void updateCategory_ReturnsCategoryDto_WhenUpdateCategoryRequestIsValid() {
        categoryDto.setId(1L);
        //Arrange
        when(categoryMapper.toEntityFromUpdateRequest(updateCategoryRequest)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);
        when(categoryRepository.countByName(updateCategoryRequest.getName())).thenReturn(1);
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(category));
        //Act
        CategoryDto result = categoryService.updateCategory(updateCategoryRequest,1L);

        //Assert
        assertNotNull(result);
        assertEquals(categoryDto, result);
        verify(categoryMapper, times(1)).toEntityFromUpdateRequest(updateCategoryRequest);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryMapper, times(1)).toDto(category);
        verify(categoryRepository, times(1)).countByName(updateCategoryRequest.getName());

    }

    @Test
    void updateCategory_ReturnsCategoryAlreadyExists_WhenUpdateCategoryRequestNameExist() {
        categoryDto.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(category));
        when(categoryRepository.countByName(updateCategoryRequest.getName())).thenReturn(2);
        assertThrows(CategoryAlreadyExistsException.class, () -> {
            categoryService.updateCategory(updateCategoryRequest,1L);
        });

        verify(categoryRepository, times(1)).countByName(updateCategoryRequest.getName());
        verify( categoryRepository, times(1)).findById(1L);

    }
    @Test
    void updateCategory_ReturnsCategoryNotFoundException_WhenUpdateCategoryIdDoesNotExist() {
        categoryDto.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> {
                 categoryService.updateCategory(updateCategoryRequest,1L);
        });
        verify( categoryRepository, times(1)).findById(1L);

    }

    @Test
    void deleteCategory_SafeDelete_WhenCategoryExists() {
        categoryDto.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(category));
        categoryService.deleteCategory(1L);
        verify(categoryRepository, times(1)).findById(1L);

    }
    @Test
    void deleteCategory_ThrowCategoryNotFoundException_WhenCategoryDoesNotExist() {
        categoryDto.setId(1L);
       when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
       assertThrows(CategoryNotFoundException.class, () -> {
           categoryService.deleteCategory(1L);
       });
       verify( categoryRepository, times(1)).findById(1L);
    }
}