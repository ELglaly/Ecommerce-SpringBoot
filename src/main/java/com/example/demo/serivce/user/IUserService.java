package com.example.demo.serivce.user;

import com.example.demo.model.dto.UserDto;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.UpdateUserRequest;

public interface IUserService {
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    UserDto getUserByUsername(String username);
    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(UpdateUserRequest request);
    void deleteUser(Long id);

}
