package com.example.ecommerce.serivce.user;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.request.user.CreateUserRequest;
import com.example.ecommerce.request.user.LoginRequest;

public interface AuthService {

    UserDTO registerUser(CreateUserRequest createUserRequest);
    void activateUser(String username);
    void resetPassword(String email);
    void resetPasswordSaved(String email, String newPassword, String confirmPassword);
    void changePassword(String token, String password, String newPassword, String confirmPassword);
    void deleteUser(String token);
    String login(LoginRequest loginRequest);
    void logout(String token);
}
