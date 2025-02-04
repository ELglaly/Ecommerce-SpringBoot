package com.example.demo.model.dto.cart;

import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {

    private Long id;
    private int quantity;
    private BigDecimal totalPrice;
    private BigDecimal unitPrice;

    private ProductDto product;

    private CartDto cart;

}
