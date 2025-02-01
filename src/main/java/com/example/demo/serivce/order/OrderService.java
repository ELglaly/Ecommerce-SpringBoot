package com.example.demo.serivce.order;

import com.example.demo.enums.OrderStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDto placeOrder(Long userId) {
        return null;
    }

    private OrderDto createOrder(CartDto cartDto) {
        OrderDto orderDto =cartDto.
        orderDto.setOrderDate(LocalDate.now());
        orderDto.setOrderStatus(OrderStatus.PENDING);
        return orderDto;
    }

    private List<OrderItem> createOrderItems(Cart cart, Order order) {
        return cart.getItems().stream().map(
                cartItem -> {
                    Product product = cartItem.getProduct();
                    product.setQuantity(product.getQuantity()- cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            cartItem.getQuantity(),
                            product,
                            order,
                            product.getPrice());
                }
        ).toList();
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(this::ConvertToOrderDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found", "Order"));
    }

    @Override
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return List.of();
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<OrderDto> getOrdersByUserIdAndStatus(Long userId, OrderStatus status) {
        return List.of();
    }

    private OrderDto ConvertToOrderDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }
}
