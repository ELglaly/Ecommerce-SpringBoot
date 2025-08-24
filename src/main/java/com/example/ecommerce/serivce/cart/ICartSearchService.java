package com.example.ecommerce.serivce.cart;

import com.example.ecommerce.dto.cart.CartDTO;
import com.example.ecommerce.entity.Cart;

public interface ICartSearchService {
    CartDTO getCartDtoById(Long id);
    Cart getCartById(Long id);
    CartDTO getCartDtoByUserId(Long userId);
    Cart getCartByUserId(Long userId);
}
