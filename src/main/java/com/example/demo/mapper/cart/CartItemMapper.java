package com.example.demo.mapper.cart;

import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.dto.cart.CartItemDto;
import com.example.demo.model.entity.CartItem;
import com.example.demo.model.entity.Product;
import com.example.demo.request.cart.AddCartItemRequest;
import com.example.demo.request.cart.UpdateCartItemRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartItemMapper implements ICartItemMapper {
    private final ModelMapper modelMapper;
    public CartItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CartItemDto toDto(CartItem entity) {
        CartItemDto cartItem= modelMapper.map(entity, CartItemDto.class);
        ProductDto productDto = modelMapper.map(entity.getProduct(), ProductDto.class);
        cartItem.setProduct(productDto);
        return cartItem;
    }

    @Override
    public CartItem toEntityFromDto(CartItemDto dto) {
        CartItem cartItem = modelMapper.map(dto, CartItem.class);
        Product product = modelMapper.map(dto.getProduct(), Product.class);
        cartItem.setProduct(product);
        return cartItem;
    }
}
