package com.example.demo.serivce.cart;

import com.example.demo.model.dto.cart.CartDto;

public interface ICartSearchService {
    CartDto getCartById(Long id);
    CartDto getCartByUserId(Long userId);
}
