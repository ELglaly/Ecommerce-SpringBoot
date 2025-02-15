package com.example.demo.serivce.user;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;

public interface IUserSearchService {
    UserDto getUserDtoById(Long id);
    User getUserById(Long id);
    UserDto getUserByEmail(String email);
    UserDto getUserByUsername(String username);
}
