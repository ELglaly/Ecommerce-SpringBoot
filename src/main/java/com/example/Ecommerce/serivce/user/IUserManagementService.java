package com.example.Ecommerce.serivce.user;

import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.request.user.CreateUserRequest;
import com.example.Ecommerce.request.user.UpdateUserRequest;

public interface IUserManagementService {
    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(String token, UpdateUserRequest request);
    void deleteUser(String token);
    void activateUser(String username);
    void changePassword(String token,String password, String newPassword, String confirmPassword);
    void resetPassword(String email);
    void resetPasswordSaved(String password, String confirmPassword);
}
