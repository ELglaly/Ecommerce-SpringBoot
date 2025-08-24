package com.example.ecommerce.controller.CategoryControllerTest;

import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.mapper.CategoryMapper;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.request.category.AddCategoryRequest;
import com.example.ecommerce.request.category.UpdateCategoryRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.response.ErrorResponse;
import com.example.ecommerce.serivce.category.CategoryService;
import com.example.ecommerce.util.CategoryUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.InstanceOfAssertFactories.ARRAY;

@ActiveProfiles("test")
@DisplayName("Category Controller Integration Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
class CategoryControllerIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @LocalServerPort
    private int port;

    private String baseUrl;

    AddCategoryRequest request = AddCategoryRequest.builder()
            .name("Electronics")
            .description("Electronic devices and gadgets")
            .images(Collections.emptyList())
            .build();

    UpdateCategoryRequest updatedRequest = UpdateCategoryRequest.builder()
            .name("Updated Electronics")
            .description("Updated electronic devices")
            .build();

    @
    BeforeAll
    static void beforeAll() {
        System.out.println("Starting CategoryController Integration Tests...");
    }
    @BeforeEach
    void setUp() {

        baseUrl = "http://localhost:" + port+"/";
        request = AddCategoryRequest.builder()
                .name("Electronics")
                .description("Electronic devices and gadgets")
                .images(Collections.emptyList())
                .build();

        updatedRequest = UpdateCategoryRequest.builder()
                .name("Updated Electronics")
                .description("Updated electronic devices")
                .build();

    }

    @Order(1)
    @ParameterizedTest(name = "{index} - Add Category with name ''{0}''")
    @ValueSource(strings = {"A", "AB"})
    void addCategory_ReturnsErrorResponse_WhenNameTooShort(String tooShortName) {
        //Arrange
        request = AddCategoryRequest.builder()
                .name(tooShortName)
                .description(request.description())
                .build();

        //Act
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl + "api/v1/categories",
                request,
                ErrorResponse.class);
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull()
                .extracting(ErrorResponse::message, ErrorResponse::statusCode)
                .contains(CategoryUtils.CATEGORY_NAME_SIZE, 400);
    }

    @Order(2)
    @ParameterizedTest(name = "{index} - Add Category with name \"{0}\" ")
    @ValueSource(strings = {"Category43", "@#Category43", "_/*8Categoty_/*"})
    @DisplayName("Add Category with Name Contains Digits and Special Characters")
    void addCategory_ReturnsErrorResponse_WhenNameContainsDigits(String invalidName) {
        //Arrange
        request = AddCategoryRequest.builder()
                .name(invalidName)
                .description(request.description())
                .build();

        //Act
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl + "api/v1/categories",
                request,
                ErrorResponse.class);
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull()
                .extracting(ErrorResponse::message, ErrorResponse::statusCode)
                .contains(CategoryUtils.CATEGORY_NAME_PATTERN_MESSAGE, 400);

    }


    @Order(3)
    @Test
    void addCategory_ReturnsCategoryDTO_WhenCategoryDataIsValid() {
        // act
        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                baseUrl + "api/v1/categories",
                request,
                ApiResponse.class);
        // ApiResponse
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull()
                .extracting(ApiResponse::getMessage)
                .isEqualTo(CategoryUtils.CATEGORY_ADDED);

        assertThat(response.getBody().getData()).isNotNull()
                .extracting( "name", "description")
                .containsExactly(request.name(), request.description());
    }

    @Test
    @Order(4)
    void addCategory_ReturnsErrorResponse_WhenCategoryExists() {
        // act
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                baseUrl + "api/v1/categories",
                request,
                ErrorResponse.class);
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull()
                .extracting(ErrorResponse::message, ErrorResponse::statusCode)
                .contains(CategoryUtils.CATEGORY_ALREADY_EXISTS, 409);
    }

    @Test
    @Order(5)
    void searchByName_ReturnsSuccess_WhenCategoryExists() {

        // Act
        String url = "/api/v1/categories/search?name=Electronics";

        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(
                "http://localhost:" + port + url,
                ApiResponse.class
        );
        // Assert
        System.out.println(Objects.requireNonNull(response.getBody()).getData().toString());

        LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) Objects.requireNonNull(response.getBody().getData());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull()
                .extracting(ApiResponse::getMessage)
                .isEqualTo(CategoryUtils.CATEGORY_FETCHED);

        assertThat(data).isNotNull()
                .extractingByKey("totalElements")
                .isEqualTo(1);
        assertThat(data).isNotNull()
                .extractingByKey("pageable")
                .isInstanceOf(LinkedHashMap.class)
                .extracting("pageSize", "pageNumber")
                .containsExactly(10, 0);

        assertThat(data).isNotNull()
                .extractingByKey("totalElements")
                .isEqualTo(1);

        assertThat(data).isNotNull()
                .extractingByKey("numberOfElements")
                .isEqualTo(1);

        assertThat(data).isNotNull()
                .extractingByKey("content")
                .asList()
                .hasSize(1)
                .extracting("name", "description")
                .containsExactly(tuple("Electronics", "Electronic devices and gadgets"));

    }

    @DisplayName("Search Category By Name - Not Found")
    @ParameterizedTest(name = "{index} - Search Category with name ''{0}''")
    @ValueSource(strings = {"NonExistentCategory", "Unknown", "RandomName","TestCategory"})
    @Order(6)
    void searchByName_ReturnsEmpty_WhenCategoryDoesNotExist(String categoryName) {
        // Arrange
        String url = UriComponentsBuilder.fromPath("api/v1/categories/search")
                .queryParam("name", categoryName)
                .build()
                .toUriString();

        System.out.println("Constructed URL: " + url);
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(
                baseUrl+  url,
                ApiResponse.class
        );
        // Assert
        LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) Objects.requireNonNull(response.getBody()).getData();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull()
                .extracting(ApiResponse::getMessage)
                .isEqualTo(CategoryUtils.CATEGORY_FETCHED);
        assertThat(data).isNotNull()
                .extractingByKey("totalElements")
                .isEqualTo(0);
        assertThat(data).isNotNull()
                .extractingByKey("pageable")
                .isInstanceOf(LinkedHashMap.class)
                .extracting("pageSize", "pageNumber")
                .containsExactly(10, 0);

        assertThat(data).isNotNull()
                .extractingByKey("totalElements")
                .isEqualTo(0);

        assertThat(data).isNotNull()
                .extractingByKey("numberOfElements")
                .isEqualTo(0);

        assertThat(data).isNotNull()
                .extractingByKey("content")
                .asList()
                .hasSize(0);

    }

    @Test
    @Order(7)
    void getAllCategories_ReturnsSuccess_WhenCategoriesExist() {
        Category category2 = Category.builder()
                .createdAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .name("Books")
                .description("Books and literature")
                .build();
        categoryRepository.save(category2);
        // Act
        String url = "api/v1/categories";
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(
                baseUrl+ url,
                ApiResponse.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        assertThat(response.getBody()).isNotNull()
                .extracting(ApiResponse::getMessage)
                .isEqualTo(CategoryUtils.CATEGORY_FETCHED);

    }


    @Test
    @Order(9)
    void getCategoryCount_ReturnsCategoriesCount_WhenCategoriesExist() {
        //Act
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/categories/count",
                ApiResponse.class
        );
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        assertThat(response.getBody()).isNotNull()
                .extracting(ApiResponse::getMessage)
                .isEqualTo(CategoryUtils.CATEGORY_FETCHED);
        assertThat(response.getBody().getData()).isNotNull()
                .isInstanceOf(Integer.class)
                .isEqualTo(2);

    }

    @Test
    @Order(16)
    void deleteCategory_ReturnsSuccess_WhenCategoryExists() {

        String url = UriComponentsBuilder.fromPath(baseUrl + "api/v1/categories/{id}")
                .buildAndExpand(1L)
                .toUriString();

        // Act
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl+  url,
                org.springframework.http.HttpMethod.DELETE,
                null,
                ApiResponse.class
        );
        categoryRepository.deleteAll();
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        assertThat(response.getBody()).isNotNull()
                .extracting(ApiResponse::getMessage)
                .isEqualTo(CategoryUtils.CATEGORY_DELETED);

        boolean exists = categoryRepository.existsById(1L);
        assertThat(exists).isFalse();


    }

    @Test
    @Order(15)
    void deleteCategory_ReturnsErrorResponse_WhenCategoryNotExists() {

        String url = UriComponentsBuilder.fromPath(baseUrl + "api/v1/categories/{id}")
                .buildAndExpand(9999L)
                .toUriString();

        // Act
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                baseUrl+  url,
                org.springframework.http.HttpMethod.DELETE,
                null,
                ErrorResponse.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull()
                .extracting(ErrorResponse::message)
                .isEqualTo(CategoryUtils.CATEGORY_NOT_FOUND);

    }


    @Test
    @Order(11)
    void updateCategory_ReturnsSuccess_WhenValidRequest() {
        // Arrange
        UpdateCategoryRequest request = UpdateCategoryRequest.builder()
                .name("Updated Electronics")
                .description("Updated electronic devices")
                .build();

        //act
        String url = UriComponentsBuilder.fromPath(baseUrl + "api/v1/categories/{id}")
                .buildAndExpand(1L)
                .toUriString();
        ResponseEntity<ApiResponse> response = restTemplate.exchange(
                baseUrl+  url,
                org.springframework.http.HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(request),
                ApiResponse.class
        );
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        assertThat(response.getBody()).isNotNull()
                .extracting(ApiResponse::getMessage)
                .isEqualTo(CategoryUtils.CATEGORY_UPDATED);
        assertThat(response.getBody().getData()).isNotNull()
                .isInstanceOf(CategoryDTO.class)
                .extracting("name", "description")
                .containsExactly(request.name(), request.description());


    }

    @Test
    @Order(10)
    void updateCategory_ReturnsNotFound_WhenCategoryDoesNotExist() {

        String url = UriComponentsBuilder.fromPath(baseUrl + "api/v1/categories/{id}")
                .buildAndExpand(999L)
                .toUriString();

        //Act
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                baseUrl+  url,
                HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(updatedRequest),
                ErrorResponse.class);
        //Assert
        System.out.println(response.toString());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull()
                .extracting(ErrorResponse::message,ErrorResponse::statusCode)
                .isEqualTo(tuple(CategoryUtils.CATEGORY_NOT_FOUND,404));
    }


    @ParameterizedTest(name = "{index} - Update Category with name ''{0}''")
    @ValueSource(strings = {"A", "AB"})
    @Order(12)
    void updateCategory_ReturnsErrorResponse_WhenNameTooShort(String tooShortName) {
        //Arrange
         updatedRequest = UpdateCategoryRequest.builder()
                .name(tooShortName)
                .description("Valid description")
                .build();

        //Act
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                baseUrl + "api/v1/categories",
                HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(updatedRequest),
                ErrorResponse.class);
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull()
                .extracting(ErrorResponse::message, ErrorResponse::statusCode)
                .contains(CategoryUtils.CATEGORY_NAME_SIZE, 400);
    }

    @ParameterizedTest(name = "{index} - Update  Category with name ''{0}'' ")
    @ValueSource(strings = {"Category43", "@#Category43", "_/*Categoty_/*"})
    @DisplayName("Update Category with Name Contains Digits and Special Characters")
    @Order(13)
    void updateCategory_ReturnsErrorResponse_WhenNameContainsDigits(String invalidName) {
        //Arrange
         updatedRequest = UpdateCategoryRequest.builder()
                .name(invalidName)
                .description("Valid description")
                .build();

        //Act
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                baseUrl + "api/v1/categories",
                HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(updatedRequest),
                ErrorResponse.class);
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull()
                .extracting(ErrorResponse::message, ErrorResponse::statusCode)
                .contains(CategoryUtils.CATEGORY_NAME_PATTERN_MESSAGE, 400);

    }

    @Test
    @Order(14)
    void updateCategory_ReturnsErrorResponse_WhenCategoryNameExists() {
        //Arrange
        updatedRequest = UpdateCategoryRequest.builder()
                .description("Valid description")
                .name("Books") // Name that already exists
                .build();
        // act
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                baseUrl + "api/v1/categories/1",
                HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(updatedRequest),
                ErrorResponse.class);
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull()
                .extracting(ErrorResponse::message, ErrorResponse::statusCode)
                .contains(CategoryUtils.CATEGORY_ALREADY_EXISTS, 409);
    }


}