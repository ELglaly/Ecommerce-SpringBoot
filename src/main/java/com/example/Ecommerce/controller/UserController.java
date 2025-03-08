package com.example.Ecommerce.controller;

import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.request.user.UpdateUserRequest;
import com.example.Ecommerce.response.ApiResponse;
import com.example.Ecommerce.serivce.user.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.example.Ecommerce.constants.ApiConstants.USER_ENDPOINT;

@RestController
@RequestMapping(USER_ENDPOINT)
public class UserController {
    private final IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = userService.getUserDtoById(id);
            return ResponseEntity.ok(new ApiResponse(userDto, "User found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse> getUserByEmail(@PathVariable String email) {
        try {
            UserDto userDto = userService.getUserByEmail(email);
            return ResponseEntity.ok(new ApiResponse(userDto, "User found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username) {
        try {
            UserDto userDto = userService.getUserByUsername(username);
            return ResponseEntity.ok(new ApiResponse(userDto, "User found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping()
    public ResponseEntity<ApiResponse> updateUser(@RequestBody @Valid UpdateUserRequest request, HttpServletResponse response) {
            String token = response.getHeader("Authorization").replace("Bearer ", "");
            UserDto userDto = userService.updateUser(token, request);
            return ResponseEntity.ok(new ApiResponse(userDto, "User updated successfully"));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(HttpServletResponse response) {
        try {
            String token = response.getHeader("Authorization").replace("Bearer ", "");
            userService.deleteUser(token);
            return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/activate/{username}")
    public ResponseEntity<ApiResponse> activateUser(@PathVariable String username, HttpServletResponse response) {
        try {
            userService.activateUser(username);
            return ResponseEntity.ok(new ApiResponse(true, "User activated successfully"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/reset-password/{email}")
    public ResponseEntity<ApiResponse> resetPassword(@PathVariable String email) {
        try {
            userService.resetPassword(email);
            return ResponseEntity.ok(new ApiResponse(true, "Password reset email sent successfully"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/reset-password-saved")
    public ResponseEntity<ApiResponse> resetPasswordSaved(@RequestParam String password, @RequestParam String confirmPassword) {
        try {
            userService.resetPasswordSaved(password, confirmPassword);
            return ResponseEntity.ok(new ApiResponse(true, "Password reset successfully"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestParam String password, @RequestParam String newPassword,
                                                      @RequestParam String confirmPassword, HttpServletResponse response) {
        try {
            String token = response.getHeader("Authorization").replace("Bearer ", "");
            userService.changePassword(token, password, newPassword, confirmPassword);
            return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
