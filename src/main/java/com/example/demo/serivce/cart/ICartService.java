package com.example.demo.serivce.cart;

import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.entity.Cart;

import java.math.BigDecimal;

public interface ICartService {


    CartDto getCartById(Long id);
    CartDto getCartByUserId(Long userId);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

}
