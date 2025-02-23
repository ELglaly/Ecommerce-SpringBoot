package com.example.Ecommerce.serivce.cart;

import com.example.Ecommerce.model.dto.cart.CartDto;
import com.example.Ecommerce.model.entity.Cart;

public interface ICartSearchService {
    CartDto getCartDtoById(Long id);
    Cart getCartById(Long id);
    CartDto getCartDtoByUserId(Long userId);
    Cart getCartByUserId(Long userId);
}
