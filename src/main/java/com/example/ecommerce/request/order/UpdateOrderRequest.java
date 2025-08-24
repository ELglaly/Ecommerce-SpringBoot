package com.example.ecommerce.request.order;

import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entity.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UpdateOrderRequest {

    private Long orderId;
    private LocalDate orderDate;
    private BigDecimal orderTotalPrice;
    private OrderStatus orderStatus;
    private Set<OrderItem> orderItems=new HashSet<>();
    private UserDTO user;

}
