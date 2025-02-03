package com.example.demo.serivce.order;

import com.example.demo.enums.OrderStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.dto.order.OrderItemDto;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.serivce.cart.CartService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;
    private final IOrderFactory orderFactory;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
                        ModelMapper modelMapper, CartService cartService, IOrderFactory orderFactory, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
        this.modelMapper = modelMapper;
        this.orderFactory = orderFactory;
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public OrderDto placeOrder(Long userId) {
        CartDto cartDto = cartService.getCartByUserId(userId);
        if (cartDto == null || cartDto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found", "User"));

        Order order = orderFactory.createOrder(cartDto, user);
        order = orderRepository.save(order);
        return ConvertToOrderDto(order);
    }
    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(this::ConvertToOrderDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found", "Order"));
    }

    @Override
    public List<OrderDto> getAllOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::ConvertToOrderDto)
                .toList();
    }

    @Override
    public List<OrderDto> getOrdersByUserIdAndStatus(Long userId, OrderStatus status) {
        return orderRepository.findByOrderStatusAndUserId(status,userId)
                .stream().map(this::ConvertToOrderDto)
                .toList();
    }

    @Override
    public BigDecimal getTotalAmount(CartDto cart) {
        return null;
    }

    private OrderDto ConvertToOrderDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setOrderItems(
                order.getOrderItems().stream()
                        .map(item -> modelMapper.map(item, OrderItemDto.class))
                        .collect(Collectors.toSet())
        );
        return orderDto;
    }
}
