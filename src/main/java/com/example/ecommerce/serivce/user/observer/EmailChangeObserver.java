package com.example.ecommerce.serivce.user.observer;

import com.example.ecommerce.entity.user.User;
import org.springframework.stereotype.Service;

@Service
public class EmailChangeObserver implements UserObserver {

    @Override
    public void update(User user) {
         System.out.println("Email changed: " + user.getUserContact().getEmail());
    }
}
