package com.example.Ecommerce.serivce.cart;

import com.example.Ecommerce.model.dto.cart.CartItemDto;
import com.example.Ecommerce.model.entity.Cart;

public interface ICartItemService {

        Cart addItemToCart(Long cartId, Long productId, int quantity);
        Cart deleteItemFromCart(Long cartId,Long itemId);
        Cart updateItemQuantity(Long cartId, Long productId, int quantity);
        CartItemDto getCartDtoItem(Long cartId, Long productId);
}
