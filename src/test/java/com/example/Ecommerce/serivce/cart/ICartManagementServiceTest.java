

package com.example.Ecommerce.serivce.cart;
import com.example.Ecommerce.DummyObjects;
import com.example.Ecommerce.exceptions.CartNotFoundException;
import com.example.Ecommerce.exceptions.product.ProductNotFoundException;
import com.example.Ecommerce.mapper.cart.ICartMapper;
import com.example.Ecommerce.model.dto.cart.CartDto;
import com.example.Ecommerce.model.entity.Cart;
import com.example.Ecommerce.model.entity.CartItem;
import com.example.Ecommerce.model.entity.Product;
import com.example.Ecommerce.model.entity.User;
import com.example.Ecommerce.repository.CartItemRepository;
import com.example.Ecommerce.repository.CartRepository;
import com.example.Ecommerce.serivce.product.IProductService;
import com.example.Ecommerce.serivce.user.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ICartManagementServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private IUserService userService;
    @Mock
    private ICartItemService cartItemService;
    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ICartMapper cartMapper;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private User user;
    private Product product;
    private CartDto cartDto;

    @BeforeEach
    void setUp() {
        DummyObjects dummyObjects=new DummyObjects();
        user = DummyObjects.user1; // Initialize user
        cart = DummyObjects.cart1; // Initialize cart
        product = DummyObjects.product1; // Initialize product
        cartDto = DummyObjects.cartDto; // Initialize cart DTO
    }

    @Test
    void addProductToCart_AddProduct_WhenProductExists() {
        // Arrange
        cart.setUser(user);
        when(cartItemService.addItemToCart(1L, 1L, 2)).thenReturn(cart);
        when(cartMapper.toDto(cart)).thenReturn(cartDto);

        // Act
        cartService.addProductToCart(1L, 1L, 2); // Cart ID, Product ID, Quantity

        // Assert
        assertEquals(1, cart.getItems().size());
        assertEquals(2, cart.getItems().stream().findFirst().get().getQuantity());
        verify(cartItemService,times(1)).addItemToCart(1L,1L,2);
        verify(cartMapper,times(1)).toDto(cart);

    }
    @Test
    void updateItem_UpdateCart_WhenProductExists() {
        // Arrange
        cart.setUser(user);
        cart.getItems().forEach(item -> item.setQuantity(1));
        when(cartItemService.updateItemQuantity(1L, 1L, 1)).thenReturn(cart);
        when(cartMapper.toDto(cart)).thenReturn(cartDto);

        // Act
        cartService.updateItem(1L, 1L, 1); // Cart ID, Product ID, Quantity

        // Assert
        assertEquals(1, cart.getItems().size());
        assertEquals(1, cart.getItems().stream().findFirst().get().getQuantity());
        verify(cartItemService,times(1)).updateItemQuantity(1L,1L,1);
        verify(cartMapper,times(1)).toDto(cart);

    }

//        @Test
//        void removeProductFromCart_RemoveProduct_WhenProductExistsInCart() {
//            // Arrange
//            CartItem cartItem = new CartItem();
//            cartItem.setProduct(product);
//            cartItem.setQuantity(2);
//            cart().add(cartItem);
//            when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
//
//            // Act
//            cartService.removeProductFromCart(1L, 1L); // Cart ID, Product ID
//
//            // Assert
//            assertTrue(cart.getCartItems().isEmpty());
//            verify(cartRepository, times(1)).findById(1L);
//            verify(cartRepository, times(1)).save(cart);
//        }
//
//        @Test
//        void removeProductFromCart_ThrowCartNotFoundException_WhenCartDoesNotExist() {
//            // Arrange
//            when(cartRepository.findById(1L)).thenReturn(Optional.empty());
//
//            // Act & Assert
//            assertThrows(CartNotFoundException.class, () -> {
//                cartService.(1L, 1L);
//            });
//            verify(cartRepository, times(1)).findById(1L);
//        }

    @Test
    void clearCart_ClearAllItems_WhenCartExists() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        doAnswer(invocation -> {
            return null; // deleteAllByCartId() returns void
        }).when(cartItemRepository).deleteAllByCartId(1L);
        // Act
        cartService.clearCart(1L);

        // Assert
        assertTrue(cart.getItems().isEmpty());
        verify(cartRepository, times(1)).findById(1L);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void clearCart_ThrowCartNotFoundException_WhenCartDoesNotExist() {
        // Arrange
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CartNotFoundException.class, () -> {
            cartService.clearCart(1L);
        });
        verify(cartRepository, times(1)).findById(1L);
    }
}
