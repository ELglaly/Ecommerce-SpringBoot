package com.example.Ecommerce.request.order;

import com.example.Ecommerce.model.dto.ProductDto;
import com.example.Ecommerce.model.dto.order.OrderDto;

import java.math.BigDecimal;

public class UpdateOrderItemRequest {

    private int quantity;
    private BigDecimal unitePrice;
    private BigDecimal totalPrice;
    private OrderDto order;

    private ProductDto product;
}
