package com.example.ecommerce.serivce.user;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.request.user.CreateUserRequest;
import com.example.ecommerce.request.user.UpdateUserRequest;

public interface IUserManagementService {
    UserDTO createUser(CreateUserRequest request);
    UserDTO updateUser(String token, UpdateUserRequest request);
    void deleteUser(String token);
    void activateUser(String username);
    void changePassword(String token,String password, String newPassword, String confirmPassword);
    void resetPassword(String email);
    void resetPasswordSaved(String password, String confirmPassword);
}
