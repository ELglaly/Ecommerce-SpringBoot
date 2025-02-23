package com.example.Ecommerce.serivce.order;

import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.exceptions.OrderNotFoundException;
import com.example.Ecommerce.mapper.order.IOrderMapper;
import com.example.Ecommerce.model.dto.order.OrderDto;
import com.example.Ecommerce.model.entity.*;
import com.example.Ecommerce.model.entity.OrderItem;
import com.example.Ecommerce.repository.OrderRepository;
import com.example.Ecommerce.request.order.AddOrderItemRequest;
import com.example.Ecommerce.request.order.CreateOrderRequest;
import com.example.Ecommerce.serivce.cart.ICartService;
import com.example.Ecommerce.serivce.product.IProductService;
import com.example.Ecommerce.serivce.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService {


    private OrderRepository orderRepository;
    private  ICartService cartService;
    private  IOrderFactory orderFactory;
    private  IProductService productService;
    private  IUserService userService;
    private  IOrderMapper orderMapper;



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


    public OrderDto placeOrder(CreateOrderRequest request) {
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
