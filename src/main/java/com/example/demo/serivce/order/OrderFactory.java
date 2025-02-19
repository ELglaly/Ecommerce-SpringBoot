package com.example.demo.serivce.order;

import com.example.demo.exceptions.InvalidFieldException;
import com.example.demo.mapper.IProductMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.entity.*;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.serivce.cart.CartService;
import com.example.demo.serivce.product.ProductService;
import com.example.demo.validator.orde.IOrderValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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

        Order order = new Order.Builder()
                .orderTotalPrice(cart.getTotalPrice())
                .user(user)
                .build();
        validateOrder(order);
        order = orderRepository.save(order);
        Set<OrderItem> orderItems = createOrderItems(cart, order);
        validateOrderItems(orderItems);
        return buildOrder(cart.getUser(), order.getOrderItems(),cart.getTotalPrice());

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

    @Override
    public Order createOrder(Product product, User user,int quantity) {
        Order order = new Order.Builder()
                .orderTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .user(user)
                .build();
        validateOrder(order);
            order = orderRepository.save(order);
            Set<OrderItem> orderItems = new HashSet<>();
            OrderItem orderItem = buildOrderItem(product, order, quantity);
            orderItems.add(orderItem);
            validateOrderItems(orderItems);
            return buildOrder(user, orderItems, order.getOrderTotalPrice());
    }

    private Set<OrderItem>  createOrderItems(Cart cart,Order order) {
        return cart.getItems().stream().map(
                cartItem -> buildOrderItem(cartItem.getProduct(), order, cartItem.getQuantity())
        ).collect(Collectors.toSet());
    }

    private OrderItem buildOrderItem(Product product,Order order, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        return new OrderItem.Builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .build();
    }

    private Order buildOrder(User user, Set<OrderItem> orderItems,BigDecimal totalPrice) {
        return new Order.Builder()
                .orderTotalPrice(totalPrice)
                .user(user)
                .orderItems(orderItems)
                .build();
    }

}
