package com.example.Ecommerce.controller;

import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.request.user.UpdateUserRequest;
import com.example.Ecommerce.response.ApiResponse;
import com.example.Ecommerce.serivce.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {

            UserDto userDto = userService.updateUser(id, request);
            return ResponseEntity.ok(new ApiResponse(userDto, "User updated successfully"));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }





}
