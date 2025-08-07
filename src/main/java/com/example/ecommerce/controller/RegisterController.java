package com.example.ecommerce.controller;

import com.example.ecommerce.constants.ApiConstants;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.request.user.CreateUserRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.serivce.user.IUserManagementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.REGISTER)
public class RegisterController {
    private final IUserManagementService userManagementService;
    public RegisterController(IUserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid CreateUserRequest createUserRequest) {
        UserDTO userDto = userManagementService.createUser(createUserRequest);
        return ResponseEntity.ok(new ApiResponse(userDto, "User created successfully"));
    }


}
