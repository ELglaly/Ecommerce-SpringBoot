package com.example.Ecommerce.model.entity;
import com.example.Ecommerce.serivce.user.observer.UserObserver;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Past(message = "Birth date must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = true)
    private Date birthDate;

    @NotBlank(message = "Username is mandatory")
    @NotNull
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @NotNull
    @Column(nullable = false)
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isActivated = false;
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isAccountNonExpired=true; // Account expiration status
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isAccountNonLocked=true; // Account lock status
    @Column(nullable = false, columnDefinition = "boolean default true")

    private boolean isCredentialsNonExpired=true; // Credentials expiration status

    @Transient
    private Set<UserObserver> observers = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhoneNumber> phoneNumber = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();

    private User(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthDate = builder.birthDate;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
        this.cart = builder.cart;
    }


    public static class Builder {
        private String firstName;
        private String lastName;
        private Date birthDate;
        private String username;
        private String password;
        private String email;
        private Set<PhoneNumber> phoneNumber = new HashSet<>();
        private Address address;
        private Set<Order> orders = new HashSet<>();
        private Cart cart;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder phoneNumber(Set<PhoneNumber> phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Builder address(Address address) {
            this.address = address;
            return this;
        }
        public Builder orders(Set<Order> orders) {
            this.orders = orders;
            return this;
        }
        public Builder cart(Cart cart) {
            this.cart = cart;
            return this;
        }
        public User build() {
            return new User(this);
        }

    }

    public void AddPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber.add(phoneNumber);
    }
}