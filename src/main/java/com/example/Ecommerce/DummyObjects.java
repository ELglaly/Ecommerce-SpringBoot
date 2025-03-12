package com.example.Ecommerce;

import com.example.Ecommerce.mapper.CategoryMapper;
import com.example.Ecommerce.mapper.ICategoryMapper;
import com.example.Ecommerce.mapper.IProductMapper;
import com.example.Ecommerce.mapper.order.IOrderMapper;
import com.example.Ecommerce.mapper.order.OrderMapper;
import com.example.Ecommerce.model.dto.CategoryDto;
import com.example.Ecommerce.model.dto.ProductDto;
import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.model.dto.cart.CartDto;
import com.example.Ecommerce.model.dto.cart.CartItemDto;
import com.example.Ecommerce.model.dto.order.OrderDto;
import com.example.Ecommerce.model.dto.order.OrderItemDto;
import com.example.Ecommerce.model.entity.*;
import com.example.Ecommerce.request.order.AddOrderItemRequest;
import com.example.Ecommerce.request.order.CreateOrderRequest;
import com.example.Ecommerce.request.product.AddProductRequest;
import com.example.Ecommerce.request.product.UpdateProductRequest;
import com.example.Ecommerce.request.user.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Component
public class DummyObjects {

    @Autowired
    private IOrderMapper orderMapper;

    private EntityManager entityManager;
    public static Product product1;
    public static Product product2;
    public static Product product3;
    public static ProductDto productDto1;
    public static ProductDto productDto2;
    public static ProductDto productDto3;
    public static AddProductRequest addProductRequest1;
    public static UpdateProductRequest updateProductRequest;

    public static OrderItem orderItem1;
    public static OrderItem orderItem2;
    public static OrderItem orderItem3;

    public static Order order1;
    public static Order order2;
    public static Order order3;
    public static OrderDto orderDto;
    public static CreateOrderRequest createOrderRequest;
    public static AddOrderItemRequest addOrderItemRequest;

    public static Category category1;
    public static Category category2;
    public static Category category3;
    public static CategoryDto categoryDto1;
    public static CategoryDto categoryDto2;
    public static CategoryDto categoryDto3;

    public static User user1;
    public static User user2;
    public static User user3;
    public static UserDto userDto1;
    public static CreateUserRequest createUserRequest1;
    public static UpdateUserRequest updateUserRequest1;
    public static AddAddressRequest addAddressRequest1;
    public static UpdateAddressRequest updateAddressRequest1;
    public static AddPhoneNumberRequest addPhoneNumberRequest1;
    public static UpdatePhoneNumberRequest updatePhoneNumberRequest1;

    public static Cart cart1;
    public static Cart cart2;
    public static Cart cart3;
    public static CartDto cartDto;

    public static CartItem cartItem1;
    public static CartItem cartItem2;
    public static CartItem cartItem3;
    public static CartItemDto cartItemDto;

    public DummyObjects() {

        createCategories();
        createProducts();
        createUsers();
        createOrderItems();
        createCartItems();
        createCarts();
        createOrders();
    }

    private void createProducts() {
        product1 = new Product.Builder()
                .name("Sample Product 1")
                .brand("Sample Brand 1")
                .description("This is a sample product description 1.")
                .price(new BigDecimal("19.99"))
                .quantity(100)
                .images(new ArrayList<>()) // Assuming you have an empty list of images
                .category(category1) // Assuming you have a default category
                .build();

        product2 = new Product.Builder()
                .name("Sample Product 2")
                .brand("Sample Brand 2")
                .description("This is a sample product description 2.")
                .price(new BigDecimal("29.99"))
                .quantity(200)
                .images(new ArrayList<>()) // Assuming you have an empty list of images
                .category(category2) // Assuming you have a default category
                .build();

        product3 = new Product.Builder()
                .name("Sample Product 3")
                .brand("Sample Brand 3")
                .description("This is a sample product description 3.")
                .price(new BigDecimal("39.99"))
                .quantity(300)
                .images(new ArrayList<>()) // Assuming you have an empty list of images
                .category(category2) // Assuming you have a default category
                .build();

        productDto1= ProductDto.builder()
                .name("Sample Product 1")
                .brand("Sample Brand 1")
                .description("This is a sample product description 1.")
                .price(new BigDecimal("19.99"))
                .quantity(100)// Assuming you have an empty list of images
                .ImageDto(null)
                .categoryDto(categoryDto1) // Assuming you have a default category
                .build();
        addProductRequest1= AddProductRequest.builder()
                .name(product1.getName())
                .brand(product1.getBrand())
                .category(category1.getName())
                .description(product1.getDescription())
                .price(product1.getPrice())
                .quantity(product1.getQuantity())
                .build();
        updateProductRequest= UpdateProductRequest.builder()
                .name("Updated Product 2")
                .brand(product1.getBrand())
                .category(category1.getName())
                .description(product1.getDescription())
                .price(product1.getPrice())
                .quantity(product1.getQuantity())
                .build();
    }

    private void createOrderItems() {
        orderItem1 = new OrderItem.Builder()
                .quantity(2)
                .product(product1) // Assuming product1 is already created
                .order(order1) // Assuming order1 is already created
                .build();

        orderItem2 = new OrderItem.Builder()
                .quantity(3)
                .product(product2) // Assuming product2 is already created
                .order(order2) // Assuming order2 is already created
                .build();

        orderItem3 = new OrderItem.Builder()
                .quantity(1)
                .product(product3) // Assuming product3 is already created
                .order(order3) // Assuming order3 is already created
                .build();
    }

    private void createOrders() {
        order1 = new Order.Builder()
                .orderTotalPrice(new BigDecimal("39.98"))
                .orderItems(Set.of(orderItem1))
                .user(user1) // Assuming user1 is already created
                .build();

        order2 = new Order.Builder()
                .orderTotalPrice(new BigDecimal("89.97"))
                .orderItems(Set.of(orderItem2))
                .user(user2) // Assuming user2 is already created
                .build();

        order3 = new Order.Builder()
                .orderTotalPrice(new BigDecimal("39.99"))
                .orderItems(Set.of(orderItem3))
                .user(user3) // Assuming user3 is already created
                .build();
      OrderItemDto orderItemDto=new OrderItemDto.Builder()
        .productDto(productDto1)
        .quantity(2)
        .build();

        orderDto=OrderDto.builder()
                .orderTotalPrice(new BigDecimal("39.98"))
                .orderStatus(order1.getOrderStatus())
                .orderDate(order1.getCreatedAt())
                .orderItems(Set.of(orderItemDto))
                .user(userDto1) // Assuming user1 is already created
                .build();

         addOrderItemRequest =AddOrderItemRequest.builder()
                .productId(1L)
                .quantity(2)
                .build();

        createOrderRequest =CreateOrderRequest.builder()
                .orderItems(Set.of(addOrderItemRequest))
                .userId(1L)
                .build();
    }

    private void createCategories() {
        category1 = new Category.Builder()
                .name("Electronics")
                .description("Category for electronic products.")
                .build();

        category2 = new Category.Builder()
                .name("Books")
                .description("Category for books and literature.")
                .build();

        category3 = new Category.Builder()
                .name("Clothing")
                .description("Category for clothing and apparel.")
                .build();

        categoryDto1 =  CategoryDto.builder()
                .name("Electronics")
                .description("Category for electronic products.")
                .build();
    }

    private void createUsers() {
        user1 = new User.Builder()
                .firstName("Mohamed")
                .lastName("Ahmed")
                .birthDate(new Date(90, 1, 1)) // Assuming birthDate is January 1, 1990
                .username("mohamedahmed")
                .password("password123")
                .email("mohamedahmed@example.com")
                .build();

        user2 = new User.Builder()
                .firstName("Ali")
                .lastName("Hassan")
                .birthDate(new Date(85, 5, 15)) // Assuming birthDate is June 15, 1985
                .username("alihassan")
                .password("password456")
                .email("alihassan@example.com")
                .build();

        user3 = new User.Builder()
                .firstName("Fatima")
                .lastName("Khaled")
                .birthDate(new Date(95, 10, 20)) // Assuming birthDate is November 20, 1995
                .username("fatimakhaled")
                .password("password789")
                .email("fatimakhaled@example.com")
                .build();
        userDto1 = UserDto.builder()
                .firstName("Mohamed")
                .lastName("Ahmed")
                .birthDate(new Date(90, 1, 1)) // Assuming birthDate is January 1, 1990
                .username("mohamedahmed")
                .email("mohamedahmed@example.com")
                .build();

        addAddressRequest1 =AddAddressRequest.builder()
                .city("Minya")
                .country("Egypt")
                .user(user1)
                .street("Samalout")
                .zip("12365")
                .build();
        updateAddressRequest1 =UpdateAddressRequest.builder()
                .city("Cairo")
                .country("Egypt")
                .user(user1)
                .street("Samalout")
                .zip("12365")
                .build();

        addPhoneNumberRequest1=AddPhoneNumberRequest.builder()
                .countryCode("+20")
                .number("1026386402")
                .build();

        updatePhoneNumberRequest1 =UpdatePhoneNumberRequest.builder()
                .countryCode("+20")
                .number("01126386402").build();

        createUserRequest1 = CreateUserRequest.builder()
                .username("Mohamed")
                .password("password123")
                .email("mohamedahmed@example.com")
                .phoneNumber(List.of(addPhoneNumberRequest1))
                .address(addAddressRequest1)
                .build();

        updateUserRequest1 = UpdateUserRequest.builder()
                .username("Mohamed")
                .firstName("Updated")
                .lastName("Ahmed")
                .birthDate(new Date(90, 1, 1))
                .email("mohamedahmed@example.com")
                .phoneNumber(List.of(updatePhoneNumberRequest1))
                .address(updateAddressRequest1)
                .build();
    }


    private void createCartItems() {
        cartItem1 = new CartItem.Builder()
                .quantity(2)
                .product(product1)
                .cart(cart1)
                .build();

        cartItem2 = new CartItem.Builder()
                .quantity(3)
                .product(product2)
                .cart(cart2)
                .build();

        cartItem3 = new CartItem.Builder()
                .quantity(1)
                .product(product3)
                .cart(cart3)
                .build();
    }
    private void createCarts() {
        cart1 = Cart.builder()
                .user(user1)
                .quantity(2)
                .totalPrice(product1.getPrice().multiply(BigDecimal.valueOf(2)))
                .id(1L)
                .items(List.of(cartItem1))
                .build();

        cartItemDto=CartItemDto.builder()
                .quantity(2)
                .product(productDto1)
                .build();

        cartDto = CartDto.builder()
                .user(userDto1)
                .totalAmount(2)
                .totalPrice(product1.getPrice().multiply(BigDecimal.valueOf(2)))
                .id(1L)
                .build();

    }
}