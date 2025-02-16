package com.example.demo.serivce.cart;

import com.example.demo.exceptions.CartNotFoundException;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.mapper.cart.CartMapper;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.cart.CartItemDto;
import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.CartItem;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.serivce.product.IProductService;
import com.example.demo.serivce.user.IUserService;
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
    private final IUserService userService;
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, CartMapper cartMapper,
                       IProductService productService, IUserService userService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService=productService;
        this.cartMapper = cartMapper;
        this.userService=userService;
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
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public void addItem(Long userId, Long productId, int amount) {

        Product product=productService.getProductById(productId);
        if(product.getQuantity()<amount)
            throw new ValidationException("Product quantity are not Enough");
        Cart cart = getCartByUserId(userId);
        // Filter the cart items to find the one with the matching product ID
        boolean itemExists = cart.getItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .peek(cartItem -> cartItem.setQuantity(cartItem.getQuantity()+amount))
                .findAny()
                .isPresent();

        if (!itemExists) {
            CartItem newCartItem = new CartItem.Builder()
                    .cart(cart)
                    .product(product)
                    .quantity(amount)
                    .build();
            cart.addItem(newCartItem);
            User user = userService.getUserById(userId);
            cart.setUser(user);
        }
        cartRepository.save(cart);
    }

    private void updateTotalPrice(Cart cart, BigDecimal price, int newQuantity) {
        // Handle null totalPrice by initializing it to BigDecimal.ZERO
        BigDecimal currentTotalPrice = cart.getTotalPrice() != null ? cart.getTotalPrice() : BigDecimal.ZERO;

        // Convert newQuantity to BigDecimal for multiplication
        BigDecimal quantity = BigDecimal.valueOf(newQuantity);

        // Calculate the new total price
        BigDecimal newTotalPrice = currentTotalPrice.add(price.multiply(quantity));

        // Set the new total price in the cart
        cart.setTotalPrice(newTotalPrice);
    }

    @Override
    public void updateItem(Long userId, Long productId, int amount) {
        Product product=productService.getProductById(productId);
        if(product.getQuantity()<amount)
            throw new ValidationException("Product quantity are not Enough");
        Cart cart = getCartByUserId(userId);
        // Filter the cart items to find the one with the matching product ID
       cart.getItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .peek(cartItem -> cartItem.setQuantity(cartItem.getQuantity()+amount));
        product.setQuantity(product.getQuantity()-amount);
        cartRepository.save(cart);

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

}
