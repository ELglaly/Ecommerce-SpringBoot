package com.example.demo.serivce.cart;


import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.entity.Cart;
import com.example.demo.model.entity.CartItem;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.serivce.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;

    public CartItemService(CartItemRepository cartItemRepository, CartService cartService,
                           ProductService productService, ModelMapper modelMapper, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.cartRepository = cartRepository;
    }

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
      Cart cart = cartService.getCart(cartId);
      Product product = modelMapper.map(productService.getProductById(productId), Product.class);
      CartItem cartItem = cart.getItems().stream()
              .filter(item -> item.getProduct().getId().equals(productId))
              .findFirst().orElse(new CartItem());
      if (cartItem.getId() == null) {
          cartItem.setProduct(product);
          cartItem.setQuantity(quantity);
          cartItem.setCart(cart);
          cartItem.setUnitPrice(product.getPrice());
      }
      else {
          cartItem.setQuantity(quantity+cartItem.getQuantity());
      }
      cartItem.setTotalPrice();
      cart.addItem(cartItem);
      cartItemRepository.save(cartItem);
      cartRepository.save(cart);
    }

    @Override
    public void deleteItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem item =getCartItem(cartId, productId);
        cart.removeItem(item);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
         Cart cart = cartService.getCart(cartId);
         cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
                 .findFirst()
                 .ifPresent(item -> {
                     item.setQuantity(quantity);
                     item.setUnitPrice(item.getProduct().getPrice());
                     item.setTotalPrice();
                         }
                 );
         cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems().stream().filter(
                item ->item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Product Not Fount","CartItem"));
    }
}
