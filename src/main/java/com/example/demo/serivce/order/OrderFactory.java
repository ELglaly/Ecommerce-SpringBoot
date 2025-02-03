package com.example.demo.serivce.order;

import com.example.demo.exceptions.InvalidFieldException;
import com.example.demo.mapper.IProductMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.repository.ProductRepository;
import com.example.demo.serivce.cart.CartService;
import com.example.demo.serivce.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderFactory implements IOrderFactory {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final IProductMapper productMapper;

    public OrderFactory(final ProductService productService, ProductRepository productRepository
    , IProductMapper productMapper) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    @Override
    public Order createOrder(CartDto cart, User user) {
        Order order = new Order.Builder()
                .orderTotalPrice(cart.getTotalPrice())
                .user(user)
                .build();
        Set<OrderItem> orderItems = createOrderItems(cart,order);
        order.setOrderItems(orderItems);
        return order;
    }

    private Set<OrderItem>  createOrderItems(CartDto cart,Order order) {
        return cart.getItems().stream().map(
                cartItem -> {
                    Product product = productMapper.toEntity(cartItem.getProduct());
                    if (product.getQuantity() < cartItem.getQuantity()) {
                        throw new InvalidFieldException("Insufficient product quantity");
                    }
                    product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem.Builder()
                            .order(order)
                            .product(product)
                            .quantity(cartItem.getQuantity())
                            .totalPrice(cartItem.getTotalPrice())
                            .unitePrice(cartItem.getUnitPrice())
                            .build();
                }
        ).collect(Collectors.toSet());
    }
}
