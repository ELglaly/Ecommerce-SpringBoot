package com.example.Ecommerce.serivce.user.observer;

import com.example.Ecommerce.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public class EmailChangeObserver implements UserObserver {

    @Override
    public void update(User user) {
         System.out.println("Email changed: " + user.getEmail());
    }
}
