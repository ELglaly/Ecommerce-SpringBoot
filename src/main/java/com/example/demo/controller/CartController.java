package com.example.demo.controller;


import com.example.demo.constants.ApiConstants;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.entity.Cart;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.cart.CartItemService;
import com.example.demo.serivce.cart.CartService;
import com.example.demo.serivce.cart.ICartItemService;
import com.example.demo.serivce.cart.ICartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.CART_ENDPOINT)
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    public CartController(CartService cartService, CartItemService cartItemService)
    {
        this.cartService = cartService;
        this.cartItemService=cartItemService;
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        CartDto cartDto =cartService.getCartDtoById(cartId);
        return ResponseEntity.ok(new ApiResponse (cartDto,"Cart Retravied Successfully"));
    }
    @DeleteMapping
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new ApiResponse (null,"Cart Retravied Successfully"));
    }
    @DeleteMapping("/item/{itemId}/{cartId}")
    public ResponseEntity<ApiResponse> removeItem(@PathVariable Long itemId, @PathVariable Long cartId ) {
        cartItemService.deleteItemFromCart(cartId   ,itemId);
        return ResponseEntity.ok(new ApiResponse (null,"Cart Retravied Successfully"));
    }
    // add product as a cartitem to cart with userid
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse> addProductToCart(@PathVariable Long userId,
                                                        @RequestParam Long productId, @RequestParam int quantity ) {
        cartService.addItem(userId,productId,quantity);
        return ResponseEntity.ok(new ApiResponse (null,"Product added to cart successfully"));
    }


}
