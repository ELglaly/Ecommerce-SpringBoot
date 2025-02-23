package com.example.Ecommerce.serivce.user;

import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.model.entity.User;
import com.example.Ecommerce.request.user.LoginRequest;

public interface IUserSearchService {
    UserDto getUserDtoById(Long id);
    User getUserById(Long id);
    UserDto getUserByEmail(String email);
    UserDto getUserByUsername(String username);
    String login(LoginRequest loginRequest);
}
