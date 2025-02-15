package com.example.demo.serivce.cart;

import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.entity.Cart;

public interface ICartSearchService {
    CartDto getCartDtoById(Long id);
    Cart getCartById(Long id);
    CartDto getCartDtoByUserId(Long userId);
    Cart getCartByUserId(Long userId);
}
