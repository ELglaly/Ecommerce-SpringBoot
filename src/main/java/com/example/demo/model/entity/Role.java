package com.example.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue( )
    private Long id;
    private String name;

    public Role(String name) {
        this.name = name;
    }


    @ManyToMany(mappedBy = "roles")
    private Collection<User> users=new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
