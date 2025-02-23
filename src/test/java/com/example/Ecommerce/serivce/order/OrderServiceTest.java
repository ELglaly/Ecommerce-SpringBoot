package com.example.Ecommerce.serivce.order;

import com.example.Ecommerce.DummyObjects;
import com.example.Ecommerce.mapper.order.IOrderMapper;
import com.example.Ecommerce.model.dto.order.OrderDto;
import com.example.Ecommerce.model.entity.Order;
import com.example.Ecommerce.model.entity.OrderItem;
import com.example.Ecommerce.repository.OrderRepository;
import com.example.Ecommerce.repository.ProductRepository;
import com.example.Ecommerce.request.order.AddOrderItemRequest;
import com.example.Ecommerce.request.order.CreateOrderRequest;
import com.example.Ecommerce.serivce.cart.ICartService;
import com.example.Ecommerce.serivce.order.IOrderFactory;
import com.example.Ecommerce.serivce.product.IProductService;
import com.example.Ecommerce.serivce.user.IUserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional // Automatically manages transactions
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrderServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ICartService cartService;

    @Mock
    private IProductService productService;

    @Mock
    private IUserService userService;

    @Mock
    private IOrderMapper orderMapper;

    @Mock
    private IOrderFactory orderFactory;

    @InjectMocks
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Initialize dummy objects
        //DummyObjects dummyObjects = new DummyObjects();

        // Use merge to handle both new and detached entities
        entityManager.merge(DummyObjects.user1);
        entityManager.merge(DummyObjects.user2);
        entityManager.merge(DummyObjects.category1);
        entityManager.merge(DummyObjects.category2);
        entityManager.merge(DummyObjects.category3);
        DummyObjects.product1= productRepository.save(DummyObjects.product1);
        entityManager.merge(DummyObjects.product2);
        entityManager.merge(DummyObjects.product3);

        // Flush changes to the database
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        // No need to manually roll back the transaction
        // Spring will handle it automatically due to @Transactional
    }

    @Test
    void placeOrder_ValidRequest_ShouldCreateAndReturnOrder() {
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest();
        AddOrderItemRequest addOrderItemRequest = new AddOrderItemRequest(1, DummyObjects.product1.getId());
        request.setOrderItems(Set.of(addOrderItemRequest));
        request.setUserId(1L);

        Set<OrderItem> orderItems = Set.of(DummyObjects.orderItem1);

        // Mock dependencies
        when(userService.getUserById(request.getUserId())).thenReturn(DummyObjects.user1);
        when(productService.getProductById(DummyObjects.product1.getId())).thenReturn(DummyObjects.product1);
        when(orderFactory.createOrder(orderItems, DummyObjects.user1)).thenReturn(DummyObjects.order1);
        when(orderRepository.save(DummyObjects.order1)).thenReturn(DummyObjects.order1);
        when(orderMapper.toDto(DummyObjects.order1)).thenReturn(new OrderDto());

        // Act
        OrderDto result = orderService.placeOrder(request); // Call the method under test

        // Assert
        assertNotNull(result);
        verify(userService).getUserById(request.getUserId());
        verify(productService).getProductById(DummyObjects.product1.getId());
        verify(orderFactory).createOrder(orderItems, DummyObjects.user1);
        verify(orderRepository).save(DummyObjects.order1);
        verify(orderMapper).toDto(DummyObjects.order1);
    }
}