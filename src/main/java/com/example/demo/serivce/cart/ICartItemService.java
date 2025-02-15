package com.example.demo.serivce.cart;

import com.example.demo.model.dto.cart.CartItemDto;
import org.springframework.context.annotation.Bean;

public interface ICartItemService {

        void addItemToCart(Long cartId, Long productId, int quantity);
        void deleteItemFromCart(Long cartId,Long itemId);
        void updateItemQuantity(Long cartId, Long productId, int quantity);
        CartItemDto getCartDtoItem(Long cartId, Long productId);
}
