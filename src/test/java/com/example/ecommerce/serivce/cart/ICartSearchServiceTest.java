

package com.example.ecommerce.serivce.cart;
import com.example.ecommerce.exceptions.CartNotFoundException;
import com.example.ecommerce.mapper.cart.CartMapper;
import com.example.ecommerce.dto.cart.CartDTO;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.serivce.product.IProductService;
import com.example.ecommerce.serivce.user.UserService;
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
        private UserService userService;
        @Mock
        private ICartItemService cartItemService;
        @Mock
        private CartItemRepository cartItemRepository;

        @Mock
        private CartMapper cartMapper;

        @InjectMocks
        private CartService cartService;

        private Cart cart;
        private User user;
        private Product product;
        private CartDTO cartDto;

        @BeforeEach
        void setUp() {

        }

        @Test
        void getCartDtoById_ReturnCartDto_WhenCartExists() {
            // Arrange
            when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
            when(cartMapper.toDto(cart)).thenReturn(cartDto);

            // Act
            CartDTO result = cartService.getCartDtoById(1L);

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
        CartDTO result = cartService.getCartDtoByUserId(1L);
        assertNotNull(result);
        assertEquals(cartDto, result);
        verify(cartRepository, times(1)).findByUserId(1L);
        verify(cartMapper, times(1)).toDto(cart);
    }

    }
