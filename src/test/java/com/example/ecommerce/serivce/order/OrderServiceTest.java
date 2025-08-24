package com.example.ecommerce.serivce.order;

import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.exceptions.OrderNotFoundException;
import com.example.ecommerce.mapper.order.OrderMapper;
import com.example.ecommerce.dto.order.OrderDTO;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.request.order.AddOrderItemRequest;
import com.example.ecommerce.request.order.CreateOrderRequest;
import com.example.ecommerce.security.jwt.JwtService;
import com.example.ecommerce.serivce.cart.ICartService;
import com.example.ecommerce.serivce.product.IProductService;
import com.example.ecommerce.serivce.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ICartService cartService;

    @Mock
    private IOrderFactory orderFactory;

    @Mock
    private IProductService productService;

    @Mock
    private UserService userService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private OrderService orderService;

    private Cart cart;
    private User user;
    private Order order;
    private OrderDTO orderDto;
    private Product product;
    private OrderItem orderItem;
    private CreateOrderRequest createOrderRequest;
    private AddOrderItemRequest addOrderItemRequest;

    @BeforeEach
    void setUp() {

    }

    @Test
    void placeOrderByCart_ReturnProductOrderDto_WhenCartIsValid() {
        // Arrange
        cart.setUser(user);
        when(cartService.getCartById(anyLong())).thenReturn(cart);
        when(orderFactory.createOrder(cart, user)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        // Act
        OrderDTO result = orderService.placeOrderByCart(1L);

        // Assert
        assertNotNull(result);
        assertEquals(orderDto, result);
        verify(cartService, times(1)).getCartById(1L);
        verify(orderFactory, times(1)).createOrder(cart, user);
        verify(orderRepository, times(1)).save(order);
        verify(orderMapper, times(1)).toDto(order);
    }

    @Test
    void placeProductOrderByCart_ThrowIllegalArgumentException_WhenCartIsEmpty() {
        // Arrange
        when(cartService.getCartById(anyLong())).thenReturn(new Cart());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.placeOrderByCart(1L);
        });
        verify(cartService, times(1)).getCartById(1L);
    }

    @Test
    void placeOrder_ReturnOrderDto_WhenProductOrderIsValid() {
        // Arrange
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn(user);
        when(productService.getProductById(1L)).thenReturn(product);
        when(orderFactory.createOrderItem(product, 2)).thenReturn(orderItem);
        when(orderFactory.createOrder(anySet(), eq(user))).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        // Act
        OrderDTO result = orderService.placeProductOrder(createOrderRequest);

        // Assert
        assertNotNull(result);
        assertEquals(orderDto, result);
        verify(userService, times(1)).getUserById(1L);
        verify(productService, times(1)).getProductById(1L);
        verify(orderFactory, times(1)).createOrderItem(product, 2);
        verify(orderFactory, times(1)).createOrder(anySet(), eq(user));
        verify(orderMapper, times(1)).toDto(order);
    }

    @Test
    void placeProductOrderItem_ThrowIllegalArgumentException_WhenQuantityExceedsAvailable() {
        // Arrange
        AddOrderItemRequest request = new AddOrderItemRequest();
        request.setProductId(1L);
        request.setQuantity(10);
        product.setQuantity(5); // Available quantity is less than requested

        when(productService.getProductById(1L)).thenReturn(product);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.placeOrderItem(request);
        });
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void getOrderById_ReturnOrderDto_WhenOrderExists() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        // Act
        OrderDTO result = orderService.getOrderById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(orderDto, result);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderMapper, times(1)).toDto(order);
    }

    @Test
    void getOrderById_ThrowOrderNotFoundException_WhenOrderDoesNotExist() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrderNotFoundException.class, () -> {
            orderService.getOrderById(1L);
        });
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getAllOrders_ReturnListOfOrderDtos_WhenOrdersExist() {
        // Arrange
        when(orderRepository.findByUserId(1L)).thenReturn(List.of(order));
        when(orderMapper.toDto(order)).thenReturn(orderDto);
        when(jwtService.extractUserId("token")).thenReturn(1L);


        // Act
        List<OrderDTO> result = orderService.getAllOrders("token");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderDto, result.get(0));
        verify(orderRepository, times(1)).findByUserId(1L);
        verify(orderMapper, times(1)).toDto(order);
    }

    @Test
    void getOrdersByUserIdAndStatus_ReturnListOfOrderDtos_WhenOrdersExist() {
        // Arrange
        when(orderRepository.findByOrderStatusAndUserId(OrderStatus.PENDING, 1L)).thenReturn(List.of(order));
        when(orderMapper.toDto(order)).thenReturn(orderDto);
        when(jwtService.extractUserId("token")).thenReturn(1L);

        // Act
        List<OrderDTO> result = orderService.getOrdersByUserIdAndStatus("token", OrderStatus.PENDING);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderDto, result.get(0));
        verify(orderRepository, times(1)).findByOrderStatusAndUserId(OrderStatus.PENDING, 1L);
        verify(orderMapper, times(1)).toDto(order);
    }

    @Test
    void calculateTotalPrice_ReturnCorrectTotal_WhenOrderItemsProvided() {

        //Arrange& Act
        BigDecimal totalPrice = orderService.calculateTotalPrice(Set.of(orderItem));
        // Assert
        assertEquals(BigDecimal.valueOf(39.98), totalPrice);
    }
}
