package com.example.ecommerce.serivce.order;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.exceptions.OrderNotFoundException;
import com.example.ecommerce.mapper.order.OrderMapper;
import com.example.ecommerce.dto.order.OrderDTO;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.request.order.AddOrderItemRequest;
import com.example.ecommerce.request.order.CreateOrderRequest;
import com.example.ecommerce.security.jwt.JwtService;
import com.example.ecommerce.serivce.cart.ICartService;
import com.example.ecommerce.serivce.product.IProductService;
import com.example.ecommerce.serivce.user.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class OrderService implements IOrderService {


    private final OrderRepository orderRepository;
    private final  ICartService cartService;
    private  final IOrderFactory orderFactory;
    private final IProductService productService;
    private final IUserService userService;
    private final OrderMapper orderMapper;
    private final JwtService jwtService;

    public OrderService(OrderRepository orderRepository, ICartService cartService,
                        IOrderFactory orderFactory, IProductService productService,
                        IUserService userService, OrderMapper orderMapper, JwtService jwtService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.orderFactory = orderFactory;
        this.productService = productService;
        this.userService = userService;
        this.orderMapper = orderMapper;
        this.jwtService = jwtService;
    }


    @Transactional
    @Override
    public OrderDTO placeOrderByCart(Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        User user = cart.getUser();
        Order order = orderFactory.createOrder(cart, user);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }
    public OrderDTO placeProductOrder(CreateOrderRequest request) {
        User user = userService.getUserById(request.getUserId());
        Set<OrderItem> orderItems = new HashSet<>();
        for (AddOrderItemRequest add : request.getOrderItems()) {
            OrderItem orderItem = placeOrderItem(add); // Process the request and create an OrderItem
            orderItems.add(orderItem); // Add the OrderItem to the set
        }
        Order order = orderFactory.createOrder(orderItems,user);
        return orderMapper.toDto(order);
    }

    public OrderItem placeOrderItem(AddOrderItemRequest request)
    {
        Product product = productService.getProductById(request.getProductId());
        if(request.getQuantity()>product.getQuantity())
        {
            throw new IllegalArgumentException("Quantity is more than available quantity");
        }
        return orderFactory.createOrderItem(product,request.getQuantity());
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(orderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDTO> getAllOrders(String token) {
        Long userId = jwtService.extractUserId(token);
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderDTO> getOrdersByUserIdAndStatus(String token, OrderStatus status) {
        Long userId = jwtService.extractUserId(token);
        return orderRepository.findByOrderStatusAndUserId(status,userId)
            .stream().map(orderMapper::toDto).toList();
    }

    @Override
    public BigDecimal calculateTotalPrice(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getTotalPrice) // Extract total price from each OrderItem
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all total prices
    }

    private int calculateOrderQuantity(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getQuantity) // Extract total price from each OrderItem
                .reduce(0,Integer::sum ); // Sum all total prices
    }

}
