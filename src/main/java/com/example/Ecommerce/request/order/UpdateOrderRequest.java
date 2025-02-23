package com.example.Ecommerce.request.order;

import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.model.entity.OrderItem;

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
    private UserDto user;

}
