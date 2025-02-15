package com.example.demo.mapper.cart;

import com.example.demo.mapper.IDtoToEntityMapper;
import com.example.demo.mapper.IEntityToDtoMapper;
import com.example.demo.mapper.IUpdateRequestToEntityMapper;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.entity.Cart;
import com.example.demo.request.cart.UpdateCartItemRequest;
import com.example.demo.request.cart.UpdateCartRequest;

public interface ICartMapper extends IDtoToEntityMapper<Cart, CartDto>
    , IEntityToDtoMapper<Cart, CartDto>
{
}
