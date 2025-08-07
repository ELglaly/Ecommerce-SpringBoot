package com.example.ecommerce.controller;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.request.user.UpdateUserRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.serivce.user.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.ecommerce.constants.ApiConstants.USER_ENDPOINT;

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
            UserDTO userDto = userService.getUserDtoById(id);
            return ResponseEntity.ok(new ApiResponse(userDto, "User found"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse> getUserByEmail(@PathVariable String email) {
            UserDTO userDto = userService.getUserByEmail(email);
            return ResponseEntity.ok(new ApiResponse(userDto, "User found"));
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username) {
            UserDTO userDto = userService.getUserByUsername(username);
            return ResponseEntity.ok(new ApiResponse(userDto, "User found"));
    }
    @PutMapping()
    public ResponseEntity<ApiResponse> updateUser(@RequestBody @Valid UpdateUserRequest request, HttpServletResponse response) {
            String token = response.getHeader("Authorization").replace("Bearer ", "");
            UserDTO userDto = userService.updateUser(token, request);
            return ResponseEntity.ok(new ApiResponse(userDto, "User updated successfully"));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(HttpServletResponse response) {
            String token = response.getHeader("Authorization").replace("Bearer ", "");
            userService.deleteUser(token);
            return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully"));
    }
    @PutMapping("/activate/{username}")
    public ResponseEntity<ApiResponse> activateUser(@PathVariable String username, HttpServletResponse response) {

        userService.activateUser(username);
        return ResponseEntity.ok(new ApiResponse(true, "User activated successfully"));
    }
    @PutMapping("/reset-password/{email}")
    public ResponseEntity<ApiResponse> resetPassword(@PathVariable String email) {

            userService.resetPassword(email);
            return ResponseEntity.ok(new ApiResponse(true, "Password reset email sent successfully"));
    }
    @PutMapping("/reset-password-saved")
    public ResponseEntity<ApiResponse> resetPasswordSaved(@RequestParam String password, @RequestParam String confirmPassword) {
            userService.resetPasswordSaved(password, confirmPassword);
            return ResponseEntity.ok(new ApiResponse(true, "Password reset successfully"));
    }
    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@RequestParam String password, @RequestParam String newPassword,
                                                      @RequestParam String confirmPassword, HttpServletResponse response) {
            String token = response.getHeader("Authorization").replace("Bearer ", "");
            userService.changePassword(token, password, newPassword, confirmPassword);
            return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully"));
    }
}
