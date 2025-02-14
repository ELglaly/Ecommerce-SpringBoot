package com.example.demo.mapper.cart;

import com.example.demo.model.dto.cart.CartItemDto;
import com.example.demo.model.entity.CartItem;
import com.example.demo.request.cart.AddCartItemRequest;
import com.example.demo.request.cart.UpdateCartItemRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper implements ICartItemMapper {
    private final ModelMapper modelMapper;
    public CartItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public CartItem toEntityFromAddRequest(AddCartItemRequest addRequest) {
        return modelMapper.map(addRequest, CartItem.class);
    }

    @Override
    public CartItemDto toDto(CartItem entity) {
        return modelMapper.map(entity, CartItemDto.class);
    }

    @Override
    public CartItem toEntityFromUpdateRequest(UpdateCartItemRequest updateRequest) {
        return modelMapper.map(updateRequest, CartItem.class);
    }
}
