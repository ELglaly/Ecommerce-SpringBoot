package com.example.Ecommerce.serivce.cart;

public interface ICartManagementService {
    void clearCart(Long id);
    void addItem(Long userId, Long productId, int amount);
    void updateItem(Long userId, Long productId, int amount);
    void checkout(Long userId);
}
