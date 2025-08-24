package com.example.ecommerce.serivce.cart;

import com.example.ecommerce.exceptions.CartNotFoundException;
import com.example.ecommerce.mapper.cart.CartMapper;
import com.example.ecommerce.dto.cart.CartDTO;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.serivce.product.IProductService;
import com.example.ecommerce.serivce.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final IProductService productService;
    private final UserService userService;
    private final ICartItemService cartItemService;
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, CartMapper cartMapper,
                       IProductService productService, UserService userService,ICartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService=productService;
        this.cartMapper = cartMapper;
        this.userService=userService;
        this.cartItemService=cartItemService;
    }

    @Override
    public CartDTO getCartDtoById(Long id) {

        return cartMapper.toDto(getCartById(id));
    }
    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not Found"));
    }
    public CartDTO getCartDtoByUserId(Long userId) {
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
        cartItemRepository.deleteAllByCartId(cart.getCartId());
        cartRepository.save(cart);
    }

    @Override
    public CartDTO updateItem(Long cartId, Long productId, int quantity) {
        Cart updatedCart=cartItemService.updateItemQuantity(cartId,productId,quantity);
        return cartMapper.toDto(updatedCart);
    }

    @Override
    public void checkout(Long userId) {

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        CartDTO cart = getCartDtoById(id);
        return null;
    }
    @Override
    public CartDTO addProductToCart(Long cartId, Long productId, int quantity) {
       Cart updatedCart = cartItemService.addItemToCart(cartId,productId,quantity);
       return cartMapper.toDto(updatedCart);
    }
}
