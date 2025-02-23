package com.example.Ecommerce.serivce.cart;


import com.example.Ecommerce.exceptions.CartNotFoundException;
import com.example.Ecommerce.exceptions.ValidationException;
import com.example.Ecommerce.exceptions.product.ProductNotFoundException;
import com.example.Ecommerce.mapper.cart.ICartItemMapper;
import com.example.Ecommerce.model.dto.cart.CartItemDto;
import com.example.Ecommerce.model.entity.Cart;
import com.example.Ecommerce.model.entity.CartItem;
import com.example.Ecommerce.model.entity.Product;
import com.example.Ecommerce.repository.CartItemRepository;
import com.example.Ecommerce.repository.CartRepository;
import com.example.Ecommerce.repository.ProductRepository;
import com.example.Ecommerce.serivce.product.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final ICartService cartService;
    private final IProductService productService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ICartItemMapper cartItemMapper;

    public CartItemService(CartItemRepository cartItemRepository, ICartService cartService,
                           IProductService productService, ModelMapper modelMapper,
                           CartRepository cartRepository,
                           ProductRepository productRepository ,
                           ICartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemMapper=cartItemMapper;
    }

    @Override
    @Transactional
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new ValidationException("Quantity must be greater than 0");
        }
        // Fetch cart and product
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("CartItem not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Validate product stock
        if (product.getQuantity() < quantity) {
            throw new ValidationException("Insufficient stock for product: " + product.getName());
        }

        // Create or update cart item
        createOrUpdateCartItem(cart, product, quantity, productId);

        cartRepository.save(cart);
    }

    private void createOrUpdateCartItem(Cart cart, Product product, int quantity, Long productId) {

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        // If the cartItem is new
        if (cartItem.getId() == null) {
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(quantity + cartItem.getQuantity());
        }
        // Add the CartItem to the cart
        cart.addItem(cartItem);

    }

    @Override
    @Transactional
    public void deleteItemFromCart(Long cartId,Long itemId) {
        CartItem cartItem= getCartItem(cartId,itemId);
        Cart cart = cartService.getCartById(cartId);
        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new ValidationException("Quantity must be greater than 0");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("CartItem not found"));

        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                });

        cartRepository.save(cart);
    }

    @Override
    public CartItemDto getCartDtoItem(Long cartId, Long itemId) {
        return cartItemMapper.toDto(getCartItem(cartId,itemId));
    }
    public CartItem getCartItem(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("CartItem not found"));

        return cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("item not found in cart"));
    }
}
