package com.example.demo.serivce.order;

import com.example.demo.enums.OrderStatus;
import com.example.demo.model.dto.order.OrderDto;

import java.util.List;

public interface IOrderService {

    OrderDto placeOrder(Long userId);
    OrderDto getOrderById(Long orderId);
    OrderDto updateOrder(Long orderId, OrderDto orderDto);
    List<OrderDto> getAllOrders();
    List<OrderDto> getOrdersByUserId(Long userId);
    List<OrderDto> getOrdersByUserIdAndStatus(Long userId, OrderStatus status);

}
