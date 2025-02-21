package com.example.Ecommerce.repository;

import com.example.Ecommerce.model.entity.Cart;
import com.example.Ecommerce.model.entity.CartItem;
import com.example.Ecommerce.model.entity.Product;
import com.example.Ecommerce.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CartRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CartRepository cartRepository;

    private User user;
    private Cart cart;
    private Product product1;
    private Product product2;
    private CartItem cartItem1;
    private CartItem cartItem2;

    @BeforeEach
    void setUp() {
        // Create a user
        user = new User.Builder()
                .birthDate(new Date())
                .username("sherif")
                .email("sherif@gmail.com")
                .password("ali@#S123654")
                .build();

        entityManager.persist(user);

        // Create a cart for the user
        cart = new Cart();
        cart.setUser(user);
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

        // Create cart items
        cartItem1 = new CartItem();
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        cartItem1.setCart(cart);
        entityManager.persist(cartItem1);

        cartItem2 = new CartItem();
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(1);
        cartItem2.setCart(cart);
        entityManager.persist(cartItem2);

        // Add items to the cart
        cart.addItem(cartItem1);
        cart.addItem(cartItem2);

        entityManager.flush();
    }

    @Test
    void testFindByUserId() {
        // Retrieve the cart by user ID
        Cart foundCart = cartRepository.findByUserId(user.getId());

        // Verify the results
        assertNotNull(foundCart);
        assertEquals(user.getId(), foundCart.getUser().getId());
        assertEquals(2, foundCart.getItems().size());
    }

    @Test
    void testAddItemToCart_ReturnsCartItems() {
        // Create a new product and cart item
        Product product3 = new Product();
        product3.setName("Product 3");
        product3.setPrice(BigDecimal.valueOf(300.0));
        entityManager.persist(product3);

        CartItem cartItem3 = new CartItem();
        cartItem3.setProduct(product3);
        cartItem3.setQuantity(3);
        cartItem3.setCart(cart);
        entityManager.persist(cartItem3);

        // Add the new item to the cart
        cart.addItem(cartItem3);

        // Save the updated cart
        Cart updatedCart = cartRepository.save(cart);

        // Verify the results
        assertEquals(3, updatedCart.getItems().size());
        //TODO : check price and amount
       // assertEquals(BigDecimal.valueOf(1300.0), updatedCart.getTotalPrice()); // 2 * 100 + 1 * 200 + 3 * 300
       // assertEquals(6, updatedCart.getTotalAmount()); // 2 + 1 + 3
    }

    @Test
    void testRemoveItemFromCart() {
        // Remove an item from the cart
        cart.removeItem(cartItem1);

        // Save the updated cart
        Cart updatedCart = cartRepository.save(cart);

        // Verify the results
        assertEquals(1, updatedCart.getItems().size());
        //TODO : check price and amount
       // assertEquals(BigDecimal.valueOf(200.0), updatedCart.getTotalPrice()); // 1 * 200
        //assertEquals(1, updatedCart.getTotalAmount()); // 1
    }

//    @Test
//    void testPostLoad() {
//        // Retrieve the cart from the database to trigger @PostLoad
//        Cart loadedCart = entityManager.find(Cart.class, cart.getId());
//
//        // Verify that @PostLoad recalculates totalPrice and totalAmount
//        assertEquals(BigDecimal.valueOf(400.0), loadedCart.getTotalPrice()); // 2 * 100 + 1 * 200
//        assertEquals(3, loadedCart.getTotalAmount()); // 2 + 1
//    }
}