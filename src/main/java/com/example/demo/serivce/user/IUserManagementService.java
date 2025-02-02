package com.example.demo.serivce.user;

import com.example.demo.model.dto.UserDto;
import com.example.demo.request.user.CreateUserRequest;
import com.example.demo.request.user.UpdateUserRequest;

public interface IUserManagementService {
    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
}
