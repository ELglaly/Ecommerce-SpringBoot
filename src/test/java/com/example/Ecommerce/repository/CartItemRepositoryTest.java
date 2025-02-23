package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.entity.Cart;
import com.example.Ecommerce.model.entity.CartItem;
import com.example.Ecommerce.model.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CartItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CartItemRepository cartItemRepository;

    private Cart cart;
    private Product product1;
    private Product product2;
    private CartItem cartItem1;
    private CartItem cartItem2;

    @BeforeEach
    void setUp() {
        // Create a cart
        cart = new Cart();
        entityManager.persist(cart);

        // Create products
        product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(BigDecimal.valueOf(100.0));
        entityManager.persist(product1);

        product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.valueOf(200.0));
        entityManager.persist(product2);

        // Create cart items using the Builder pattern
        cartItem1 = new CartItem.Builder()
                .quantity(2)
                .product(product1)
                .cart(cart)
                .build();
        entityManager.persist(cartItem1);

        cartItem2 = new CartItem.Builder()
                .quantity(1)
                .product(product2)
                .cart(cart)
                .build();
        entityManager.persist(cartItem2);

        entityManager.flush();
    }

    @Test
    void testCartItemBuilder() {
        // Verify that the Builder pattern works correctly
        assertEquals(2, cartItem1.getQuantity());
        assertEquals(product1, cartItem1.getProduct());
        assertEquals(cart, cartItem1.getCart());

        assertEquals(1, cartItem2.getQuantity());
        assertEquals(product2, cartItem2.getProduct());
        assertEquals(cart, cartItem2.getCart());
    }

//    @Test
//    void testPostLoad() {
//        // Retrieve cartItem1 from the database to trigger @PostLoad
//        Optional<CartItem> loadedCartItem = cartItemRepository.findById(cartItem1.getId());
//
//        // Verify that @PostLoad sets unitPrice and totalPrice correctly
//        assertTrue(loadedCartItem.isPresent());
//        assertNotNull(loadedCartItem.get().getUnitPrice());
//        assertEquals(product1.getPrice(), loadedCartItem.get().getUnitPrice());
//
//        assertNotNull(loadedCartItem.get().getTotalPrice());
//        assertEquals(product1.getPrice().multiply(BigDecimal.valueOf(loadedCartItem.get().getQuantity())), loadedCartItem.get().getTotalPrice());
//    }

    @Test
    void testDeleteAllByCartId() {
        cartItemRepository.deleteAllByCartId(cart.getId());

        List<CartItem> remainingCartItems = cartItemRepository.findAllByCartId(cart.getId());
        assertTrue(remainingCartItems.isEmpty());
    }

    @Test
    void testFindAllByCartId_ReturnsListOfCartItems() {
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());

        assertEquals(2, cartItems.size());
        assertTrue(cartItems.contains(cartItem1));
        assertTrue(cartItems.contains(cartItem2));
    }
}