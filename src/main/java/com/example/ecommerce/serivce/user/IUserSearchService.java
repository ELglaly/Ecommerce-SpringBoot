package com.example.ecommerce.serivce.user;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.request.user.LoginRequest;

public interface IUserSearchService {
    UserDTO getUserDtoById(Long id);
    User getUserById(Long id);
    UserDTO getUserByEmail(String email);
    UserDTO getUserByUsername(String username);
    String authenticate(LoginRequest loginRequest);

}
