package com.example.ecommerce.request.order;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.order.OrderDTO;

import java.math.BigDecimal;

public class UpdateOrderItemRequest {

    private int quantity;
    private BigDecimal unitePrice;
    private BigDecimal totalPrice;
    private OrderDTO order;

    private ProductDTO product;
}
