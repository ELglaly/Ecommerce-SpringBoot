package com.example.ecommerce.serivce.user;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.request.user.UpdateUserRequest;

public interface UserManagementService {
    UserDTO updateUser(String token, UpdateUserRequest request);
}
