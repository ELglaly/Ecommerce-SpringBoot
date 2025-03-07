package com.example.Ecommerce.serivce.user;

import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.request.user.CreateUserRequest;
import com.example.Ecommerce.request.user.UpdateUserRequest;

public interface IUserManagementService {
    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
    void activateUser(String username);
}
