package com.example.ecommerce.controller;

import com.example.ecommerce.constants.ApiConstants;
import com.example.ecommerce.request.user.LoginRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.serivce.user.IUserService;
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
        String JwtToken = userService.authenticate(loginRequest);
        return ResponseEntity.ok(new ApiResponse(JwtToken, "Login successful"));
    }
    // TODO: Implement logout endpoint
    // TODO: Implement login with Google endpoint
    @GetMapping("/google")
    public String loginWithGoogle()
    {
        return "Login with Google";
    }
}
