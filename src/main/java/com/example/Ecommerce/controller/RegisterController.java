package com.example.Ecommerce.controller;

import com.example.Ecommerce.constants.ApiConstants;
import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.request.user.CreateUserRequest;
import com.example.Ecommerce.response.ApiResponse;
import com.example.Ecommerce.serivce.user.IUserManagementService;
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
        UserDto userDto = userManagementService.createUser(createUserRequest);
        return ResponseEntity.ok(new ApiResponse(userDto, "User created successfully"));
    }


}
