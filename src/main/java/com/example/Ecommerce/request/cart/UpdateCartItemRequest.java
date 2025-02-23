package com.example.Ecommerce.request.cart;

import com.example.Ecommerce.model.dto.ProductDto;
import com.example.Ecommerce.model.dto.cart.CartDto;

import java.math.BigDecimal;

public class UpdateCartItemRequest {

    private int quantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;

    private ProductDto product;

    private CartDto cart;

}
