package com.example.Ecommerce.repository;

import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.model.entity.User;
import com.example.Ecommerce.model.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static com.example.Ecommerce.enums.OrderStatus.PENDING;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    private User user1;
    private User user2;
    private Order order1;
    private Order order2;
    private Order order3;

    @BeforeEach
    void setUp() {
        // Create and persist users
        user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        entityManager.persist(user1);

        user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        entityManager.persist(user2);

        // Create and persist orders
        order1 = new Order.Builder()
                .user(user1)
                .build();
        entityManager.persist(order1);

        order2 = new Order.Builder()
                .user(user1)
                .build();
        order2.updateStatus(OrderStatus.SHIPPED);
        entityManager.persist(order2);

        order3 =  new Order.Builder()
                .user(user1)
                .build();
        entityManager.persist(order3);

        entityManager.flush();
    }

    @Test
    void testFindByUserId() {
        // Test finding orders by user ID
        List<Order> orders = orderRepository.findByUserId(user1.getId());

        // Verify the results
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    void testFindByOrderStatusAndUserId() {
        // Test finding orders by order status and user ID
        List<Order> orders = orderRepository.findByOrderStatusAndUserId(PENDING, user1.getId());

        // Verify the results
        assertEquals(1, orders.size());
        assertTrue(orders.contains(order1));
    }

    @Test
    void testFindByUserId_NoOrders() {
        // Test finding orders for a user with no orders
        List<Order> orders = orderRepository.findByUserId(999L); // Non-existent user ID

        // Verify the results
        assertTrue(orders.isEmpty());
    }

    @Test
    void testFindByOrderStatusAndUserId_NoOrders() {
        // Test finding orders by order status and user ID for a user with no matching orders
        List<Order> orders = orderRepository.findByOrderStatusAndUserId(OrderStatus.SHIPPED, user2.getId());

        // Verify the results
        assertTrue(orders.isEmpty());
    }
}