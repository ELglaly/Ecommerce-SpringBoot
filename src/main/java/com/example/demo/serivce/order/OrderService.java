package com.example.demo.serivce.order;

import com.example.demo.enums.OrderStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.dto.order.OrderItemDto;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.serivce.cart.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
                        ModelMapper modelMapper, CartService cartService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDto placeOrder(Long userId) {
        CartDto cartdto = cartService.getCartByUserId(userId);
        Order order = createOrder(cartdto);
        return ConvertToOrderDto(order);
    }

    private Order createOrder(CartDto cart) {
        Order order = new Order();
        order.setOrderItems(createOrderItems(cart,order));
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order = orderRepository.save(order);
        return order;
    }

    private Set<OrderItem> createOrderItems(CartDto cart, Order order) {
        return cart.getItems().stream().map(
                cartItem -> {
                    Product product = modelMapper.map(cartItem.getProduct(), Product.class);
                    product.setQuantity(product.getQuantity()- cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            cartItem.getQuantity(),
                            product,
                            order,
                            product.getPrice());
                }
        ).collect(Collectors.toSet());
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
