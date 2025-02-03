package com.example.demo.model.entity;
import com.example.demo.serivce.user.observer.UserObserver;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @Transient
    private Set<UserObserver> observers = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhoneNumber> phoneNumber = new HashSet<>();

    @Getter
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    @Getter
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;



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

    public @NotBlank String getFirstName() {
        return firstName;
    }

    public @NotBlank String getLastName() {
        return lastName;
    }

    public @Past Date getBirthDate() {
        return birthDate;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public @NotBlank @Size(min = 8, max = 20) String getPassword() {
        return password;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }


    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NotBlank String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(@Past Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public void setPassword(@NotBlank @Size(min = 8, max = 20) String password) {
        this.password = password;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public Set<UserObserver> getObservers() {
        return observers;
    }

    public void addObserver(UserObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(UserObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers() {
        observers.forEach(observer -> observer.update(this));
    }
}