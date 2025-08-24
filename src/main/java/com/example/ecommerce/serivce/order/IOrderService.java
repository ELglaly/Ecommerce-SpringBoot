package com.example.ecommerce.serivce.order;

import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.dto.order.OrderDTO;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.request.order.CreateOrderRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface IOrderService {

    OrderDTO placeOrderByCart(Long cartId);
    OrderDTO getOrderById(Long orderId);
    List<OrderDTO> getAllOrders(String token);
    List<OrderDTO> getOrdersByUserIdAndStatus(String token, OrderStatus status);

    BigDecimal calculateTotalPrice(Set<OrderItem> orderItems);

    OrderDTO placeProductOrder(CreateOrderRequest request);
}
