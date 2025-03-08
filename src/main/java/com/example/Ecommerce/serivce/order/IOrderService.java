package com.example.Ecommerce.serivce.order;

import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.model.dto.order.OrderDto;
import com.example.Ecommerce.model.entity.OrderItem;
import com.example.Ecommerce.request.order.CreateOrderRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface IOrderService {

    OrderDto placeOrderByCart(Long cartId);
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getAllOrders(String token);
    List<OrderDto> getOrdersByUserIdAndStatus(String token, OrderStatus status);

    BigDecimal calculateTotalPrice(Set<OrderItem> orderItems);

    OrderDto placeProductOrder(CreateOrderRequest request);
}
