package com.example.Ecommerce.mapper.cart;

import com.example.Ecommerce.model.dto.ProductDto;
import com.example.Ecommerce.model.dto.cart.CartItemDto;
import com.example.Ecommerce.model.entity.CartItem;
import com.example.Ecommerce.model.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
