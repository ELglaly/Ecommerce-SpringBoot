package com.example.ecommerce.entity;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.exceptions.InvalidFieldException;
import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Builder
@NoArgsConstructor
@Table(name = "orders")
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @Column(name = "order_number", unique = true)
    private String orderNumber;


    @Column(name = "order_notes")
    private String notes;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "EGY";

    @DecimalMin(value = "0.01", message = "Tax must be positive")
    @Digits(integer = 8, fraction = 2, message = "Invalid Tax format")
    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @NotNull()
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @NotNull
    @DecimalMin(value = "0.01", message = "Order total must be positive")
    @Digits(integer = 8, fraction = 2, message = "Invalid price format")
    @Column(name = "order_total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal orderTotalPrice;

    @NotEmpty(message = "Order must contain at least one item")
    @Size(min = 1, max = 100, message = "Order must have between 1 and 100 items")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems = new HashSet<>();


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

}
