package com.example.Ecommerce.mapper.cart;

import com.example.Ecommerce.mapper.IDtoToEntityMapper;
import com.example.Ecommerce.mapper.IEntityToDtoMapper;
import com.example.Ecommerce.model.dto.cart.CartDto;
import com.example.Ecommerce.model.entity.Cart;

public interface ICartMapper extends IDtoToEntityMapper<Cart, CartDto>
    , IEntityToDtoMapper<Cart, CartDto>
{
}
