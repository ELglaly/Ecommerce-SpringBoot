package com.example.ecommerce.mapper.cart;

import com.example.ecommerce.dto.cart.CartDTO;
import com.example.ecommerce.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartMapper
{
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDTO toDto(Cart cart);

    Cart toEntity(CartDTO cartDTO);

}
