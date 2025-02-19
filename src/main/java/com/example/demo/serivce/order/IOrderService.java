package com.example.demo.serivce.order;

import com.example.demo.enums.OrderStatus;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.order.OrderDto;

import java.math.BigDecimal;
import java.util.List;

public interface IOrderService {

    OrderDto placeOrderByCart(Long userId);
    OrderDto placeOrderByProduct(Long productId, Long userId, int quantity);
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getAllOrders(Long userId);
    List<OrderDto> getOrdersByUserIdAndStatus(Long userId, OrderStatus status);
}
