package com.example.demo.request.cart;

import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.dto.cart.CartDto;

import java.math.BigDecimal;

public class UpdateCartItemRequest {

    private int quantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;

    private ProductDto product;

    private CartDto cart;

}
