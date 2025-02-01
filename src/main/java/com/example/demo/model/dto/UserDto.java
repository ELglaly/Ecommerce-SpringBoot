package com.example.demo.model.dto;

import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.entity.Address;
import com.example.demo.model.entity.PhoneNumber;

import java.util.List;
import java.util.Set;

public class UserDto {


    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<PhoneNumber> phoneNumber;
    private Address address;

    private Set<OrderDto> orders;
    private CartDto cart;


    public CartDto getCart() {
        return cart;
    }

    public void setCart(CartDto cart) {
        this.cart = cart;
    }

    public Set<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderDto> orders) {
        this.orders = orders;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<PhoneNumber> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<PhoneNumber> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
