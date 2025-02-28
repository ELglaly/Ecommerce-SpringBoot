

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
class ICartSearchServiceTest {

        @Mock
        private CartRepository cartRepository;

        @Mock
        private IProductService productService;

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
        void getCartDtoById_ReturnCartDto_WhenCartExists() {
            // Arrange
            when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
            when(cartMapper.toDto(cart)).thenReturn(cartDto);

            // Act
            CartDto result = cartService.getCartDtoById(1L);

            // Assert
            assertNotNull(result);
            assertEquals(cartDto, result);
            verify(cartRepository, times(1)).findById(1L);
            verify(cartMapper, times(1)).toDto(cart);
        }

        @Test
        void getCartById_ThrowCartNotFoundException_WhenCartDoesNotExist() {
            // Arrange
            when(cartRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(CartNotFoundException.class, () -> {
                cartService.getCartById(1L);
            });
            verify(cartRepository, times(1)).findById(1L);
        }

    @Test
    void getCartDtoByUserId_ReturnCartDto_WhenCartExists() {
        // Arrange
        when(cartRepository.findByUserId(1L)).thenReturn(cart);
        when(cartMapper.toDto(cart)).thenReturn(cartDto);

        // Act & Assert
        CartDto result = cartService.getCartDtoByUserId(1L);
        assertNotNull(result);
        assertEquals(cartDto, result);
        verify(cartRepository, times(1)).findByUserId(1L);
        verify(cartMapper, times(1)).toDto(cart);
    }

    }
