package com.example.demo.request.order;

import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.dto.order.OrderDto;

import java.math.BigDecimal;

public class AddOrderItemRequest {
    private Long id;
    private int quantity;
    private BigDecimal unitePrice;
    private BigDecimal totalPrice;
    private OrderDto order;

    private ProductDto product;
}
