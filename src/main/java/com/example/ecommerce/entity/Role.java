package com.example.ecommerce.entity;

import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User role is mandatory")
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @ManyToMany()
    private Collection<User> users = new HashSet<>();

}
