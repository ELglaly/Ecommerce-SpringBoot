package com.example.Ecommerce.serivce.order;

import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.model.dto.order.OrderDto;
import com.example.Ecommerce.model.entity.OrderItem;
import com.example.Ecommerce.request.order.CreateOrderRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface IOrderService {

    OrderDto placeOrderByCart(Long userId);
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getAllOrders(Long userId);
    List<OrderDto> getOrdersByUserIdAndStatus(Long userId, OrderStatus status);

    BigDecimal calculateTotalPrice(Set<OrderItem> orderItems);

    OrderDto placeOrder(CreateOrderRequest request);
}
