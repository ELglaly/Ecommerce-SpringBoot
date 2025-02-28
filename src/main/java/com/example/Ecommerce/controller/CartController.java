package com.example.Ecommerce.controller;


import com.example.Ecommerce.constants.ApiConstants;
import com.example.Ecommerce.model.dto.cart.CartDto;
import com.example.Ecommerce.response.ApiResponse;
import com.example.Ecommerce.serivce.cart.ICartItemService;
import com.example.Ecommerce.serivce.cart.ICartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.CART_ENDPOINT)
public class CartController {
    private final ICartService cartService;
    private final ICartItemService cartItemService;
    public CartController(ICartService cartService, ICartItemService cartItemService)
    {
        this.cartService = cartService;
        this.cartItemService=cartItemService;
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        CartDto cartDto =cartService.getCartDtoById(cartId);
        return ResponseEntity.ok(new ApiResponse (cartDto,"Cart Retravied Successfully"));
    }
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new ApiResponse (null,"Cart Retravied Successfully"));
    }
    @DeleteMapping("/item/{itemId}/{cartId}")
    public ResponseEntity<ApiResponse> removeItem(@PathVariable Long itemId, @PathVariable Long cartId ) {
        cartItemService.deleteItemFromCart(cartId ,itemId);
        return ResponseEntity.ok(new ApiResponse (null,"item removed Successfully"));
    }
    // add product as a cartitem to cart with userid
    @PostMapping("/{cartId}")
    public ResponseEntity<ApiResponse> addProductToCart(@PathVariable Long cartId,
                                                        @RequestParam Long productId, @RequestParam int quantity ) {
        CartDto cartDto= cartService.addProductToCart(cartId,productId,quantity);
        return ResponseEntity.ok(new ApiResponse (cartDto,"Product added to cart successfully"));
    }



}
