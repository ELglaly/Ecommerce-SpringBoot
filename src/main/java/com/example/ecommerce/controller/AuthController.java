package com.example.ecommerce.controller;


import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.request.user.CreateUserRequest;
import com.example.ecommerce.request.user.LoginRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.serivce.user.AuthService;
import com.example.ecommerce.serivce.user.UserManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "authController")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // This controller will handle authentication-related endpoints
    // For example, login, register, etc.
    // Currently, it is empty and can be filled with methods as needed.
    // Future methods can include:
    // - User registration
    // - User login
    // - Password reset
    // - Token generation and validation
    // - Social login integration
    // - Two-factor authentication setup and verification
    // Each method will be implemented as needed, following best practices for security and user experience.

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(new ApiResponse(token, "Login successful"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid CreateUserRequest request) {
        UserDTO userDTO = authService.registerUser(request);
        return ResponseEntity.status(201).body(new  ApiResponse(userDTO, "Registration successful"));
    }

    @GetMapping("/google")
    public String loginWithGoogle()
    {
        return "Login with Google";
    }


    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        authService.deleteUser(token);
        return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully"));
    }

    public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        authService.logout(token);
        return ResponseEntity.ok(new ApiResponse(null, "User fetched successfully"));
    }

    @PutMapping("/activate/{username}")
    public ResponseEntity<ApiResponse> activateUser(@PathVariable String username, HttpServletRequest request) {
        authService.activateUser(username);
        return ResponseEntity.ok(new ApiResponse(true, "User activated successfully"));
    }

    @PutMapping("/reset-password/{email}")
    public ResponseEntity<ApiResponse> resetPassword(@PathVariable String email) {
        authService.resetPassword(email);
        return ResponseEntity.ok(new ApiResponse(true, "Password reset email sent successfully"));
    }
    @PutMapping("/reset-password-saved")
    public ResponseEntity<ApiResponse> resetPasswordSaved(@RequestParam
                                                              @Email(message = "Invalid email format")
                                                              @NotBlank(message = "Email cannot be blank")
                                                              String email,
                                                          @RequestParam String password,
                                                          @RequestParam String confirmPassword) {
        authService.resetPasswordSaved(email,password, confirmPassword);
        return ResponseEntity.ok(new ApiResponse(true, "Password reset successfully"));
    }
    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestParam String password, @RequestParam String newPassword,
                                                      @RequestParam String confirmPassword, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        authService.changePassword(token, password, newPassword, confirmPassword);
        return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully"));
    }
}
