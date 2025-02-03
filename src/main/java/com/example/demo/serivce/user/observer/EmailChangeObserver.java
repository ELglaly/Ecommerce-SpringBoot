package com.example.demo.serivce.user.observer;

import com.example.demo.model.entity.User;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Observer;
@Service
public class EmailChangeObserver implements UserObserver {

    @Override
    public void update(User user) {
         System.out.println("Email changed: " + user.getEmail());
    }
}
