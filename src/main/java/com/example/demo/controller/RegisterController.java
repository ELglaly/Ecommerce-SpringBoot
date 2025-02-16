package com.example.demo.controller;

import com.example.demo.constants.ApiConstants;
import com.example.demo.model.dto.UserDto;
import com.example.demo.request.user.CreateUserRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.user.IUserManagementService;
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
    // TODO: Implement register with Google endpoint

}
