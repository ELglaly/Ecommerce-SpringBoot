package com.example.demo.model.dto.order;

import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class OrderItemDto {

    private Long id;
    private int quantity;
    private BigDecimal unitePrice;
    private BigDecimal totalPrice;
    private OrderDto order;
    private ProductDto product;
}
