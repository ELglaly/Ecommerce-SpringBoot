package com.example.demo.serivce.cart;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.CartItem;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart getCart(Long id) {
        Cart cart= cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not Found", "Cart"));
        BigDecimal totalPrice = cart.getTotalPrice();
        cart.setTotalPrice(totalPrice);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {

        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
