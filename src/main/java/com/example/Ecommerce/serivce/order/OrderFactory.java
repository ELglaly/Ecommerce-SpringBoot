package com.example.Ecommerce.serivce.order;

import com.example.Ecommerce.exceptions.InvalidFieldException;
import com.example.Ecommerce.model.entity.*;
import com.example.Ecommerce.model.entity.OrderItem;
import com.example.Ecommerce.repository.OrderRepository;
import com.example.Ecommerce.repository.ProductRepository;
import com.example.Ecommerce.validator.orde.IOrderValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
        Order order = new Order.Builder()
                .orderTotalPrice(cart.getTotalPrice())
                .orderItems(orderItems)
                .user(user)
                .build();
        validateOrder(order);
        return order;

    }
    private Set<OrderItem> createOrderItems(Cart cart) {
        return cart.getItems().stream().map(
                cartItem -> createOrderItem(cartItem.getProduct(), cartItem.getQuantity())
        ).collect(Collectors.toSet());
    }


    @Override
    public Order createOrder(Set<OrderItem> orderItems, User user) {
       // BigDecimal totalPrice = orderService.calculateTotalPrice(orderItems);
        Order order = buildOrder(user,orderItems,BigDecimal.valueOf(20));
        validateOrder(order);
        return order;
    }
    private Order buildOrder(User user, Set<OrderItem> orderItems,BigDecimal totalPrice) {
        return new Order.Builder()
                .orderTotalPrice(totalPrice)
                .user(user)
                .orderItems(orderItems)
                .build();
    }

    public OrderItem createOrderItem(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        return new OrderItem.Builder()
                .product(product)
                .quantity(quantity)
                .build();
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
