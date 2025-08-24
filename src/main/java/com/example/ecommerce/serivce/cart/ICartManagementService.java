package com.example.ecommerce.serivce.cart;

import com.example.ecommerce.dto.cart.CartDTO;

public interface ICartManagementService {
    void clearCart(Long id);
    CartDTO addProductToCart(Long cartId, Long productId, int quantity);
    CartDTO updateItem(Long cartId, Long productId, int quantity);
    void checkout(Long userId);
}
