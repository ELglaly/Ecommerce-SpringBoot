package com.example.Ecommerce.serivce.cart;

import com.example.Ecommerce.exceptions.CartNotFoundException;
import com.example.Ecommerce.exceptions.ValidationException;
import com.example.Ecommerce.mapper.cart.CartMapper;
import com.example.Ecommerce.mapper.cart.ICartMapper;
import com.example.Ecommerce.model.dto.cart.CartDto;
import com.example.Ecommerce.model.dto.cart.CartItemDto;
import com.example.Ecommerce.model.entity.Cart;
import com.example.Ecommerce.model.entity.CartItem;
import com.example.Ecommerce.model.entity.Product;
import com.example.Ecommerce.model.entity.User;
import com.example.Ecommerce.repository.CartItemRepository;
import com.example.Ecommerce.repository.CartRepository;
import com.example.Ecommerce.serivce.product.IProductService;
import com.example.Ecommerce.serivce.user.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ICartMapper cartMapper;
    private final IProductService productService;
    private final IUserService userService;
    private final ICartItemService cartItemService;
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ICartMapper cartMapper,
                       IProductService productService, IUserService userService,ICartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService=productService;
        this.cartMapper = cartMapper;
        this.userService=userService;
        this.cartItemService=cartItemService;
    }

    @Override
    public CartDto getCartDtoById(Long id) {

        return cartMapper.toDto(getCartById(id));
    }
    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not Found"));
    }
    public CartDto getCartDtoByUserId(Long userId) {
        return cartMapper.toDto(getCartByUserId(userId));

    }
    public Cart getCartByUserId(Long userId) {
        return Optional.ofNullable(cartRepository.findByUserId(userId))
                .orElse(new Cart());
    }

    @Override
    @Transactional
    public void clearCart(Long cartId) {
        Cart cart = getCartById(cartId);
        cartItemRepository.deleteAllByCartId(cart.getId());
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);
    }

    @Override
    public CartDto updateItem(Long cartId, Long productId, int quantity) {
        Cart updatedCart=cartItemService.updateItemQuantity(cartId,productId,quantity);
        return cartMapper.toDto(updatedCart);
    }

    @Override
    public void checkout(Long userId) {

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        CartDto cart = getCartDtoById(id);
        return cart.getItems().stream()
                .map(CartItemDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Override
    public CartDto addProductToCart(Long cartId, Long productId, int quantity) {
       Cart updatedCart = cartItemService.addItemToCart(cartId,productId,quantity);
       return cartMapper.toDto(updatedCart);
    }
}
