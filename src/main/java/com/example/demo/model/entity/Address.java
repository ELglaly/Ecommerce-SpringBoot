package com.example.demo.model.entity;

import com.example.demo.exceptions.ValidationException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
