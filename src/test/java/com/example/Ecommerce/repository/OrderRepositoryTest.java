package com.example.Ecommerce.repository;

import com.example.Ecommerce.enums.OrderStatus;
import com.example.Ecommerce.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Product product1;
    private Product product2;
    private Product product3;
    private Set<OrderItem> orderItem1=new HashSet<>();
    private Set<OrderItem>  orderItem2=new HashSet<>();
    private Set<OrderItem>  orderItem3=new HashSet<>();

    @BeforeEach
    void setUp() {
        // Create and persist users
        setUsers();
        setProducts();
        setOrderItems();

        order1 = new Order.Builder()
                .orderItems(orderItem1)
                .user(user1)
                .build();
        entityManager.persist(order1);

        order2 = new Order.Builder()
                .orderItems(orderItem2)
                .user(user1)
                .build();
        order2.updateStatus(OrderStatus.SHIPPED);
        entityManager.persist(order2);

        order3 =  new Order.Builder()
                .orderItems(orderItem3)
                .user(user1)
                .build();
        entityManager.persist(order3);

        entityManager.flush();
    }

    void setUsers(){
        user1 = new User.Builder()
                .birthDate(new Date())
                .username("sherif")
                .email("sherif@gmail.com")
                .password("ali@#S123654")
                .build();

        entityManager.persist(user1);

        user2 = new User.Builder()
                .birthDate(new Date())
                .username("sherif2")
                .email("sherif2@gmail.com")
                .password("ali@#S123654")
                .build();
    }
    void setOrderItems()
    {
        OrderItem items1 = new OrderItem.Builder()
                .product(product1)
                .quantity(1)
                .order(order1)
                .build();
        entityManager.persist(items1);
        OrderItem items2 = new OrderItem.Builder()
                .product(product2)
                .quantity(5)
                .order(order2)
                .build();
        entityManager.persist(items2);
        OrderItem items3  = new OrderItem.Builder()
                .product(product3)
                .quantity(2)
                .order(order3)
                .build();
        entityManager.persist(items3);

        orderItem1.add((OrderItem) orderItem1);
        orderItem1.add((OrderItem) orderItem2);

        orderItem1.add((OrderItem) orderItem3);

    }
    void setProducts()
    {
        product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(BigDecimal.valueOf(100.0));
        entityManager.persist(product1);

        product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.valueOf(200.0));
        entityManager.persist(product2);

    }

    @Test
    void testFindByUserId_ReturnsListOfOrder() {
        // Test finding orders by user ID
        List<Order> orders = orderRepository.findByUserId(user1.getId());

        // Verify the results
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    void testFindByOrderStatusAndUserId_ReturnsListOfOrder() {
        // Test finding orders by order status and user ID
        List<Order> orders = orderRepository.findByOrderStatusAndUserId(PENDING, user1.getId());

        // Verify the results
        assertEquals(1, orders.size());
        assertTrue(orders.contains(order1));
    }

    @Test
    void testFindByUserId__ReturnsListOfOrderNotFound() {
        // Test finding orders for a user with no orders
        List<Order> orders = orderRepository.findByUserId(999L); // Non-existent user ID

        // Verify the results
        assertTrue(orders.isEmpty());
    }

    @Test
    void testFindByOrderStatusAndUserId_ReturnsListOfOrderNotFound() {
        // Test finding orders by order status and user ID for a user with no matching orders
        List<Order> orders = orderRepository.findByOrderStatusAndUserId(OrderStatus.SHIPPED, user2.getId());

        // Verify the results
        assertTrue(orders.isEmpty());
    }
}