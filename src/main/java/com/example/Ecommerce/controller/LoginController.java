package com.example.Ecommerce.controller;

import com.example.Ecommerce.constants.ApiConstants;
import com.example.Ecommerce.request.user.LoginRequest;
import com.example.Ecommerce.response.ApiResponse;
import com.example.Ecommerce.serivce.user.IUserService;
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
        String JwtToken = userService.login(loginRequest);
        return ResponseEntity.ok(new ApiResponse(JwtToken, "Login successful"));
    }
    // TODO: Implement logout endpoint
    // TODO: Implement login with Google endpoint
    @GetMapping("/google")
    public String loginWithGoogle()
    {
        return "hkxckdv";
    }
    // TODO: Implement login with Facebook endpoint
    // TODO: Implement login with Twitter endpoint
    // TODO: Implement login with LinkedIn endpoint
    // TODO: Implement login with GitHub endpoint
    // TODO: Implement login with Bitbucket endpoint
    // TODO: Implement login with StackOverflow endpoint
}
