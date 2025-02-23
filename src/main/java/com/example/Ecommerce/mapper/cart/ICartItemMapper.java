package com.example.Ecommerce.mapper.cart;

import com.example.Ecommerce.mapper.IDtoToEntityMapper;
import com.example.Ecommerce.mapper.IEntityToDtoMapper;
import com.example.Ecommerce.model.dto.cart.CartItemDto;
import com.example.Ecommerce.model.entity.CartItem;

public interface ICartItemMapper extends IEntityToDtoMapper<CartItem, CartItemDto>
    , IDtoToEntityMapper<CartItem, CartItemDto>
{
}
