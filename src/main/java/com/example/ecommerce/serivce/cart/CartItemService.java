package com.example.ecommerce.serivce.cart;


import com.example.ecommerce.exceptions.CartNotFoundException;
import com.example.ecommerce.exceptions.ValidationException;
import com.example.ecommerce.exceptions.product.ProductNotFoundException;
import com.example.ecommerce.mapper.cart.CartItemMapper;
import com.example.ecommerce.dto.cart.CartItemDTO;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.serivce.product.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;

@Service
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    //private final ICartService cartService;
    private final IProductService productService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemMapper cartItemMapper;

    String rootPath = Paths.get("").toAbsolutePath().toString();

    public CartItemService(CartItemRepository cartItemRepository,
                           IProductService productService, ModelMapper modelMapper,
                           CartRepository cartRepository,
                           ProductRepository productRepository ,
                           CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
       // this.cartService = cartService;
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemMapper=cartItemMapper;
    }

    @Override
    @Transactional
    public Cart addItemToCart(Long cartId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new ValidationException(rootPath,"Quantity must be greater than 0");
        }
        // Fetch cart and product
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(rootPath));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Validate product stock
        if (product.getQuantity() < quantity) {
            throw new ValidationException(rootPath,"Insufficient stock for product: " + product.getName());
        }

        // Create or update cart item
        createOrUpdateCartItem(cart, product, quantity, productId);
        return cartRepository.save(cart);
    }

    private void createOrUpdateCartItem(Cart cart, Product product, int quantity, Long productId) {
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        // If the cartItem is new
        if (cartItem.getId() == null) {
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            //cartItem.setCart(cart);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(quantity + cartItem.getQuantity());
        }
    }

    @Override
    @Transactional
    public Cart deleteItemFromCart(Long cartId,Long itemId) {
        CartItem cartItem= getCartItem(cartId,itemId);
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow();
        cartItemRepository.delete(cartItem);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart updateItemQuantity(Long cartId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new ValidationException(rootPath,"Quantity must be greater than 0");
        }
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(rootPath));

        return cartRepository.save(cart);
    }

    @Override
    public CartItemDTO getCartDtoItem(Long cartId, Long itemId) {
        return cartItemMapper.toDto(getCartItem(cartId,itemId));
    }
    private CartItem getCartItem(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(rootPath));

        return cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(rootPath,"item not found in cart"));
    }

}
