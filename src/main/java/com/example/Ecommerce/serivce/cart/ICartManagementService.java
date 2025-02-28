package com.example.Ecommerce.serivce.cart;

import com.example.Ecommerce.model.dto.cart.CartDto;

public interface ICartManagementService {
    void clearCart(Long id);
    CartDto addProductToCart(Long cartId, Long productId, int quantity);
    CartDto updateItem(Long cartId, Long productId, int quantity);
    void checkout(Long userId);
}
