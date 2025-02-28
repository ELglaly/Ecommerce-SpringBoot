package com.example.Ecommerce.mapper.cart;

import com.example.Ecommerce.model.dto.cart.CartDto;
import com.example.Ecommerce.model.dto.cart.CartItemDto;
import com.example.Ecommerce.model.entity.Cart;
import com.example.Ecommerce.model.entity.CartItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CartMapper implements ICartMapper {
    private final ModelMapper modelMapper;
    private final CartItemMapper cartItemMapper;
    public CartMapper(ModelMapper modelMapper, CartItemMapper cartItemMapper) {
        this.modelMapper = modelMapper;
        this.cartItemMapper = cartItemMapper;
    }
    @Override
    public Cart toEntityFromDto(CartDto dto) {
        Cart cart = modelMapper.map(dto, Cart.class);
        Optional.ofNullable(dto.getItems()).ifPresent(items -> {
            List<CartItem> cartItems = new ArrayList<>();
            items.forEach(item -> {
                cartItems.add(cartItemMapper.toEntityFromDto(item));
            });
            cart.setItems(cartItems);
        });
        return cart;
    }

    @Override
    public CartDto toDto(Cart entity) {
        CartDto cartDto = modelMapper.map(entity, CartDto.class);
        Optional.ofNullable(entity.getItems()).ifPresent(items -> {
            Set<CartItemDto> itemDtos = new HashSet<>();
            items.forEach(item -> {
                itemDtos.add (cartItemMapper.toDto(item));
            });
            cartDto.setItems(itemDtos);
        });
        return cartDto;
    }
}
