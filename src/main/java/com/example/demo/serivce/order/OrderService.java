package com.example.demo.serivce.order;

import com.example.demo.enums.OrderStatus;
import com.example.demo.exceptions.OrderNotFoundException;
import com.example.demo.exceptions.user.UserNotFoundException;
import com.example.demo.mapper.order.IOrderMapper;
import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.dto.order.OrderItemDto;
import com.example.demo.model.entity.*;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.serivce.cart.CartService;
import com.example.demo.serivce.cart.ICartService;
import com.example.demo.serivce.product.IProductService;
import com.example.demo.serivce.user.IUserService;
import com.example.demo.serivce.user.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;
    private final IOrderFactory orderFactory;
    private final IProductService productService;
    private final IUserService userService;
    private final IOrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository ,
                        ModelMapper modelMapper, ICartService cartService, IOrderFactory orderFactory
    ,IProductService productService,IUserService userService,IOrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.modelMapper = modelMapper;
        this.orderFactory = orderFactory;
        this.productService=productService;
        this.userService=userService;
        this.orderMapper=orderMapper;
    }


    @Transactional
    @Override
    public OrderDto placeOrderByCart(Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        User user = cart.getUser();
        Order order = orderFactory.createOrder(cart, user);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto placeOrderByProduct(Long productId, Long userId, int quantity) {
        Product product = productService.getProductById(productId);
        User user = userService.getUserById(userId);
        if(quantity>product.getQuantity())
        {
            throw new IllegalArgumentException("Quantity is more than available quantity");
        }
        return orderMapper.toDto(orderFactory.createOrder(product, user, quantity));
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(orderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getAllOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderDto> getOrdersByUserIdAndStatus(Long userId, OrderStatus status) {
        return orderRepository.findByOrderStatusAndUserId(status,userId)
            .stream().map(orderMapper::toDto).toList();
    }
}
