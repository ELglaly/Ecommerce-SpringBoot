package com.example.Ecommerce.serivce.cart;

import com.example.Ecommerce.DummyObjects;
import com.example.Ecommerce.exceptions.CartNotFoundException;
import com.example.Ecommerce.exceptions.ValidationException;
import com.example.Ecommerce.exceptions.product.ProductNotFoundException;
import com.example.Ecommerce.mapper.cart.ICartItemMapper;
import com.example.Ecommerce.mapper.cart.ICartMapper;
import com.example.Ecommerce.model.dto.cart.CartDto;
import com.example.Ecommerce.model.dto.cart.CartItemDto;
import com.example.Ecommerce.model.entity.Cart;
import com.example.Ecommerce.model.entity.CartItem;
import com.example.Ecommerce.model.entity.Product;
import com.example.Ecommerce.repository.CartItemRepository;
import com.example.Ecommerce.repository.CartRepository;
import com.example.Ecommerce.repository.ProductRepository;
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
class CartItemServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ICartItemMapper cartItemMapper;
    @Mock
    private ICartService cartService;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartItemService cartItemService;

    private Cart cart;
    private Product product;
    private CartItem cartItem;
    private CartItemDto cartItemDto;

    @BeforeEach
    void setUp() {
        DummyObjects dummyObjects = new DummyObjects();
        cart = DummyObjects.cart1; // Initialize cart
        product = DummyObjects.product1; // Initialize product
        cartItem =DummyObjects.cartItem1; // Initialize cart item
        cartItemDto =DummyObjects.cartItemDto;
    }

    @Test
    void addItemToCart_AddItem_WhenCartExists() {
        // Arrange
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        // Act
        cart.setId(1L);
        product.setId(1L);
        Cart result = cartItemService.addItemToCart(cart.getId(), product.getId(), 1);

        // Assert
        assertNotNull(result);
        assertEquals(cartItem, result.getItems());
        verify(cartRepository, times(1)).findById(cart.getId());
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    void addItemToCart_ThrowCartNotFoundException_WhenCartDoesNotExist() {
        // Arrange
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CartNotFoundException.class, () -> {
            cartItemService.addItemToCart(cart.getId(), product.getId(), 1);
        });
        verify(cartRepository, times(1)).findById(cart.getId());
    }

    @Test
    void addItemToCart_ThrowProductNotFoundException_WhenProductDoesNotExist() {
        // Arrange
        product.setId(100L);
        cart.setId(1L);
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.ofNullable(cart));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            cartItemService.addItemToCart(cart.getId(), product.getId(), 1);
        });
        verify(cartRepository, times(1)).findById(cart.getId());
        verify(productRepository,times(1)).findById(1L);
    }
    @Test
    void addItemToCart_ThrowValidationException_WhenQuantityIsLessThanZero() {
        // Act & Assert
        assertThrows(ValidationException.class, () -> {
            cartItemService.addItemToCart(cart.getId(), product.getId(), -100);
        });
    }
    @Test
    void addItemToCart_ThrowValidationException_WhenQuantityIsMoreThanProductQuantity() {
        product.setId(100L);
        cart.setId(1L);
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.ofNullable(cart));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ValidationException.class, () -> {
            cartItemService.addItemToCart(cart.getId(), product.getId(), 500000);
        });
        verify(cartRepository, times(1)).findById(cart.getId());
        verify(productRepository,times(1)).findById(1L);
    }

    @Test
    void deleteItemFromCart_RemoveItem_WhenItemExists() {
        // Arrange
        cart.setId(1L);
        cartItem.setId(1L);
        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.of(cartItem));
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.ofNullable(cart));
        when(cartService.getCartById(cart.getId())).thenReturn(cart);

        // Act
        Cart result= cartItemService.deleteItemFromCart(cart.getId(),cartItem.getId());
        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());

        // Assert
        verify(cartItemRepository, times(1)).deleteById(cartItem.getId());
    }

    @Test
    void deleteItemFromCart_ThrowProductNotFoundException_WhenItemDoesNotExist() {
        // Arrange
        cart.setId(1L);
        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            cartItemService.deleteItemFromCart(cart.getId(),100L);
        });
        verify(cartItemRepository, times(1)).findById(cartItem.getId());
    }

    @Test
    void updateItemQuantity_UpdateQuantity_WhenCartExists() {
        // Arrange
        cart.setId(1L);
        product.setId(1L);
        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.of(cartItem));
        cartItem.setQuantity(2);
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        // Act
        Cart result = cartItemService.updateItemQuantity(cart.getId(),product.getId(), 1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getItems().stream().findFirst().get().getQuantity());
        verify(cartItemRepository, times(1)).findById(cartItem.getId());
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    void updateItemQuantity_ThrowProductNotFoundException_WhenProductDoesNotExist() {
        // Arrange
        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            Cart result = cartItemService.updateItemQuantity(cart.getId(),product.getId(), 1);
        });
        verify(cartItemRepository, times(1)).findById(cartItem.getId());
    }

    @Test
    void getCartDtoItem_ReturnCartItem_WhenItemExists() {
        // Arrange
        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.of(cartItem));
        when(cartRepository.findById(1L)).thenReturn( Optional.of(cart));
        when(cartItemMapper.toDto(cartItem)).thenReturn(cartItemDto);

        // Act
        CartItemDto result = cartItemService.getCartDtoItem(cart.getId(), cartItem.getId());

        // Assert
        assertNotNull(result);
        assertEquals(cartItem, result);
        verify(cartItemRepository, times(1)).findById(cartItem.getId());
        verify(cartItemMapper,times(1)).toDto(cartItem);
        verify(cartRepository,times(1)).findById(1L);

    }

    @Test
    void getCartDtoItem_ThrowProductNotFoundException_WhenItemDoesNotExist() {
        // Arrange
        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            cartItemService.getCartDtoItem( cart.getId(),cartItem.getId());
        });
        verify(cartItemRepository, times(1)).findById(cartItem.getId());
    }
}
