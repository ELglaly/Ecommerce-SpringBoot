package com.example.ecommerce.request.cart;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.cart.CartDTO;

import java.math.BigDecimal;

public class UpdateCartItemRequest {

    private int quantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;

    private ProductDTO product;

    private CartDTO cart;

}
