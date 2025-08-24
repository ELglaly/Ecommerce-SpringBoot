package com.example.ecommerce.entity;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "EGY";

    @DecimalMin(value = "0.01", message = "Tax must be positive")
    @Digits(integer = 8, fraction = 2, message = "Invalid Tax format")
    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @NotNull
    @DecimalMin(value = "0.01", message = "Order total must be positive")
    @Digits(integer = 8, fraction = 2, message = "Invalid price format")
    @Column(name = "order_total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal cartTotalPrice;

    @NotEmpty(message = "Order must contain at least one item")
    @Size(min = 1, max = 100, message = "Order must have between 1 and 100 items")
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CartItem> cartItems = new HashSet<>();

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

}
