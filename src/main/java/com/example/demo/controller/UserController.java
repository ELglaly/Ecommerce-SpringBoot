package com.example.demo.controller;

import com.example.demo.serivce.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.constants.ApiConstants.USER_ENDPOINT;

@RestController
@RequestMapping(USER_ENDPOINT)
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
