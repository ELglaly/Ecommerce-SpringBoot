package com.example.demo.serivce.user;

import com.example.demo.model.dto.UserDto;

public interface IUserSearchService {
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    UserDto getUserByUsername(String username);
}
