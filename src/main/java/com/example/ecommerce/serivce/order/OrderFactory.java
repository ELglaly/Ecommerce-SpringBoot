package com.example.ecommerce.serivce.order;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.exceptions.InvalidFieldException;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.validator.orde.IOrderValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderFactory implements IOrderFactory {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final IOrderValidator orderValidator;

    public OrderFactory(ProductRepository productRepository, OrderRepository orderRepository
    , IOrderValidator orderValidator) {
        this.productRepository = productRepository;
        this.orderRepository=orderRepository;
        this.orderValidator=orderValidator;
    }

    @Transactional
    @Override
    public Order createOrder(Cart cart, User user) {

        Set<OrderItem> orderItems = createOrderItems(cart);
        validateOrderItems(orderItems);
        // BigDecimal totalPrice = orderService.calculateTotalPrice(orderItems);
        Order order = buildOrder(user, orderItems, BigDecimal.valueOf(20));
        validateOrder(order);
        return order;

    }
    private Set<OrderItem> createOrderItems(Cart cart) {
       return null;
    }


    @Override
    public Order createOrder(Set<OrderItem> orderItems, User user) {
       // BigDecimal totalPrice = orderService.calculateTotalPrice(orderItems);
        Order order = buildOrder(user,orderItems,BigDecimal.valueOf(20));
        validateOrder(order);
        return order;
    }
    private Order buildOrder(User user, Set<OrderItem> orderItems,BigDecimal totalPrice) {
     return null;
    }

    public OrderItem createOrderItem(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        return new OrderItem();
    }

    private void validateOrderItems(Set<OrderItem> orderItems) {
        if (!orderValidator.orderItemsIsValid(orderItems)) {
            throw new InvalidFieldException("order items");
        }
    }
    private void validateOrder(Order order) {
        if (!orderValidator.orderIsValid(order)) {
            throw new InvalidFieldException("order");
        }
    }

}
