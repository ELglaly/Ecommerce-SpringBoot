package com.example.Ecommerce.serivce.cart;

import com.example.Ecommerce.model.dto.cart.CartItemDto;

public interface ICartItemService {

        void addItemToCart(Long cartId, Long productId, int quantity);
        void deleteItemFromCart(Long cartId,Long itemId);
        void updateItemQuantity(Long cartId, Long productId, int quantity);
        CartItemDto getCartDtoItem(Long cartId, Long productId);
}
