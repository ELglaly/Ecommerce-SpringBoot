package com.example.demo.serivce.cart;


import com.example.demo.exceptions.CartNotFoundException;
import com.example.demo.exceptions.ValidationException;
import com.example.demo.exceptions.product.ProductNotFoundException;
import com.example.demo.mapper.cart.CartItemMapper;
import com.example.demo.mapper.cart.ICartItemMapper;
import com.example.demo.model.dto.cart.CartItemDto;
import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.CartItem;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.serivce.product.IProductService;
import com.example.demo.serivce.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemService cartItemService;
    private final CartItemMapper cartItemMapper;

    public CartItemService(CartItemRepository cartItemRepository, CartService cartService,
                           ProductService productService, ModelMapper modelMapper,
                           CartRepository cartRepository,
                           ProductRepository productRepository,CartItemService cartItemService,
                           CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemService=cartItemService;
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
        // Find the CartItem in the cart for the given product ID
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId)) // Filter items by product ID
                .findFirst() // Get the first matching item (if any)
                .orElse(new CartItem()); // If no matching item is found, create a new CartItem

        // If the cartItem is new
        if (cartItem.getId() == null) {
            // Set the product, quantity, cart, and unit price for the new CartItem
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            // If the cartItem  exists, update its quantity by adding the new quantity
            cartItem.setQuantity(quantity + cartItem.getQuantity());
        }
        // Add the CartItem to the cart
        cart.addItem(cartItem);

    }

    @Override
    @Transactional
    public void deleteItemFromCart(Long cartId,Long itemId) {
        CartItem cartItem= getCartItem(cartId,itemId);
        cartItemRepository.delete(cartItem);
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
