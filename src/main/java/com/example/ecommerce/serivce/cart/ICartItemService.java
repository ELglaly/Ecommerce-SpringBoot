package com.example.ecommerce.serivce.cart;

import com.example.ecommerce.dto.cart.CartItemDTO;
import com.example.ecommerce.entity.Cart;

public interface ICartItemService {

        Cart addItemToCart(Long cartId, Long productId, int quantity);
        Cart deleteItemFromCart(Long cartId,Long itemId);
        Cart updateItemQuantity(Long cartId, Long productId, int quantity);
        CartItemDTO getCartDtoItem(Long cartId, Long productId);
}
