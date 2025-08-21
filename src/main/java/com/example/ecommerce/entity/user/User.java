package com.example.ecommerce.entity.user;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.serivce.user.observer.UserObserver;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

import static com.example.ecommerce.constants.ErrorMessages.UserError.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@ToString
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Size(min = 2, max = 50, message = FIRST_NAME_SIZE)
    @Pattern(regexp = "^[a-zA-Z]+$", message = FIRST_NAME_PATTERN)
    private String firstName;


    @Size(min = 2, max = 50, message = LAST_NAME_SIZE)
    @Pattern(regexp = "^[a-zA-Z]+$", message = LAST_NAME_PATTERN)
    private String lastName;

    @Past(message = BIRTH_DATE_PAST)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotBlank(message = USERNAME_EMPTY)
    @Size(min = 3, max = 50, message = USERNAME_SIZE)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = USERNAME_PATTERN)
    @Column(unique = true, nullable = false)
    private String username;

    @Transient
    private Set<UserObserver> observers = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<Role> roles = new HashSet<>();

    @Embedded
    private UserContact userContact;

    @Embedded
    private UserSecurity userSecurity;

}