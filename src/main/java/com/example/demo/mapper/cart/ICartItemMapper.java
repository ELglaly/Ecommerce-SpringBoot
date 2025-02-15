package com.example.demo.mapper.cart;

import com.example.demo.mapper.IAddRequestToEntityMapper;
import com.example.demo.mapper.IDtoToEntityMapper;
import com.example.demo.mapper.IEntityToDtoMapper;
import com.example.demo.mapper.IUpdateRequestToEntityMapper;
import com.example.demo.model.dto.cart.CartItemDto;
import com.example.demo.model.entity.CartItem;
import com.example.demo.request.cart.AddCartItemRequest;
import com.example.demo.request.cart.UpdateCartItemRequest;

public interface ICartItemMapper extends IEntityToDtoMapper<CartItem, CartItemDto>
    , IDtoToEntityMapper<CartItem, CartItemDto>
{
}
