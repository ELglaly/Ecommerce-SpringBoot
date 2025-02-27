package com.example.Ecommerce.serivce.category;

import com.example.Ecommerce.DummyObjects;
import com.example.Ecommerce.exceptions.category.CategoryNotFoundException;
import com.example.Ecommerce.mapper.CategoryMapper;
import com.example.Ecommerce.mapper.ICategoryMapper;
import com.example.Ecommerce.mapper.IProductMapper;
import com.example.Ecommerce.model.dto.CategoryDto;
import com.example.Ecommerce.model.entity.Category;
import com.example.Ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ICategorySearchServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private IProductMapper productMapper;

    @Mock
    private ICategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categorySearchService;

    private Category category;
    private CategoryDto categoryDto;

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
    }

    @Test
    void getCategoryById_ReturnsCategoryDto_WhenIdExists() {
        // Arrange
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // Act
        CategoryDto result = categorySearchService.getCategoryById(categoryId);

        // Assert
        assertNotNull(result);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryMapper, times(1)).toDto(category);
    }

    @Test
    void getCategoryById_ThrowsResourceNotFoundException_WhenIdDoesNotExist() {
        // Arrange
        Long categoryId = 999L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> {
            categorySearchService.getCategoryById(categoryId);
        });

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryMapper, never()).toDto(any());
    }
    @Test
    void getAllCategories_ReturnsAllCategories_WhenCategoriesExist() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category,category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // Act
        List<CategoryDto> categoryDtos = categorySearchService.getAllCategories();


        assertNotNull(categoryDtos);
        assertEquals(2, categoryDtos.size());
        assertEquals(categoryDto, categoryDtos.get(0));
        assertEquals(categoryDto, categoryDtos.get(1));
        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, times(2)).toDto(any());
    }
    @Test
    void getAllCategories_ReturnsEmptyList_WhenNoCategoriesExist() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<CategoryDto> categoryDtos = categorySearchService.getAllCategories();

        assertNotNull(categoryDtos);
        assertTrue(categoryDtos.isEmpty());
        verify(categoryRepository, times(1)).findAll();
    }
    @Test
    void getCategoryByName_ReturnsCategoryDto_WhenIdExists() {
        // Arrange
        when(categoryRepository.findByName(category.getName())).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // Act
        CategoryDto result = categorySearchService.getCategoryByName(category.getName());


        // Assert
        assertNotNull(result);
        assertEquals(categoryDto, result);
        verify(categoryRepository, times(1)).findByName(category.getName());
        verify(categoryMapper, times(1)).toDto(category);
    }

    @Test
    void getCategoryByName_ThrowsResourceNotFoundException_WhenIdDoesNotExist() {
        // Arrange
        when(categoryRepository.findByName("Error")).thenReturn(null);

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> {
            categorySearchService.getCategoryByName("Error");
        });

        verify(categoryRepository, times(1)).findByName("Error");
    }

}