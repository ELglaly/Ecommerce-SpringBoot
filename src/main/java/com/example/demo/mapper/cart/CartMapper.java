package com.example.demo.mapper.cart;

import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.cart.CartItemDto;
import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.CartItem;
import com.example.demo.repository.CartRepository;
import com.example.demo.request.cart.UpdateCartRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        //TODO : CartItemMapper
        return null;
    }

    @Override
    public CartDto toDto(Cart entity) {
        CartDto dto = modelMapper.map(entity, CartDto.class);
        List<CartItemDto> cartItemDto =new ArrayList<>();
        entity.getItems().forEach(item -> {
            cartItemDto.add(cartItemMapper.toDto(item));
        });
        return dto;
    }

    @Override
    public Cart toEntityFromUpdateRequest(UpdateCartRequest updateRequest) {
        Cart cart = modelMapper.map(updateRequest, Cart.class);
        Set<CartItem> cartItem =new HashSet<>();
        updateRequest.getItems().forEach(item -> {
            cartItem.add(cartItemMapper.toEntityFromUpdateRequest(item));
        });
        cart.setItems(cartItem);
        return cart;
    }
}
