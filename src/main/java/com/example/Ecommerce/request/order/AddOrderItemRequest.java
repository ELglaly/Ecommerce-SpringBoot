package com.example.Ecommerce.request.order;

import com.example.Ecommerce.model.dto.ProductDto;
import com.example.Ecommerce.model.dto.order.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AddOrderItemRequest {

    private int quantity;
    private Long productId;
}
