package com.example.demo.controller;

import com.example.demo.constants.ApiConstants;
import com.example.demo.model.dto.UserDto;
import com.example.demo.request.user.LoginRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.LOGIN)
public class LoginController {
    private final IUserService userService;
    public LoginController(IUserService userService) {
        this.userService = userService;
    }

    // TODO: Implement login endpoint
    // implement login endpoint
   @PostMapping
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        String userDto = userService.login(loginRequest);
        return ResponseEntity.ok(new ApiResponse(userDto, "Login successful"));
    }
    // TODO: Implement logout endpoint
    // TODO: Implement login with Google endpoint
}
