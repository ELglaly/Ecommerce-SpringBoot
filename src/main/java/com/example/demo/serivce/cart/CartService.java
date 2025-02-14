package com.example.demo.serivce.cart;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.mapper.cart.CartItemMapper;
import com.example.demo.mapper.cart.CartMapper;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.cart.CartItemDto;
import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.CartItem;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public CartDto getCartById(Long id) {
        return cartRepository.findById(id).map(cartMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not Found", "Cart"));
    }
    public CartDto getCartByUserId(Long userId) {
        return Optional.ofNullable(cartRepository.findByUserId(userId))
                .map(cartMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not Found", "Cart"));

    }

    @Override
    public void clearCart(Long userId) {
        CartDto cart = getCartByUserId(userId);
        cartItemRepository.deleteAllByCartId(cart.getId());
        cart.getItems().clear();
        cartRepository.deleteById(cart.getId());
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        CartDto cart = getCartById(id);
        return cart.getItems().stream()
                .map(CartItemDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
