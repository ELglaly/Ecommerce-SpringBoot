package com.example.ecommerce.mapper.cart;


import com.example.ecommerce.dto.cart.CartItemDTO;
import com.example.ecommerce.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface CartItemMapper
{
    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    CartItemDTO toDto(CartItem cartItem);

    CartItem toEntity(CartItemDTO cartItemDTO);

}
