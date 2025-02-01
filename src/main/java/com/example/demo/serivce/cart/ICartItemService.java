package com.example.demo.serivce.cart;

import com.example.demo.model.dto.cart.CartItemDto;
import com.example.demo.model.entity.CartItem;

import java.util.List;

public interface ICartItemService {

        void addItemToCart(Long cartId, Long productId, int quantity);
        void deleteItemFromCart(Long cartId, Long productId);
        void updateItemQuantity(Long cartId, Long productId, int quantity);
        CartItemDto getCartItem(Long cartId, Long productId);
}
