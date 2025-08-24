package com.example.ecommerce.controller;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.request.user.UpdateUserRequest;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.serivce.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.ecommerce.constants.ApiConstants.USER_ENDPOINT;

@RestController
@RequestMapping(USER_ENDPOINT)
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
            UserDTO userDto = userService.getUserDtoById(id);
            return ResponseEntity.ok(new ApiResponse(userDto, "User found"));
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
    public ResponseEntity<ApiResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateRequest, HttpServletRequest request ) {
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            UserDTO userDto = userService.updateUser(token, updateRequest);
            return ResponseEntity.ok(new ApiResponse(userDto, "User updated successfully"));

    }

}
