package com.example.demo.controller;

import com.example.demo.model.dto.UserDto;
import com.example.demo.request.user.CreateUserRequest;
import com.example.demo.request.user.UpdateUserRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.constants.ApiConstants.USER_ENDPOINT;

@RestController
@RequestMapping(USER_ENDPOINT)
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        UserDto userDto = userService.createUser(request);
        return ResponseEntity.ok(new ApiResponse(userDto, "User created successfully"));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = userService.getUserById(id);
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
        try {
            UserDto userDto = userService.updateUser(id, request);
            return ResponseEntity.ok(new ApiResponse(userDto, "User updated successfully"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
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
