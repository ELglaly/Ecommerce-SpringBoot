package com.example.demo.serivce.user;

import com.example.demo.exceptions.ResourceAlreadyExistsException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.CreateUserRequest;
import com.example.demo.request.UpdateUserRequest;
import org.modelmapper.ModelMapper;

import java.util.Optional;

public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToUserDto)
                .orElseThrow(()->new ResourceNotFoundException("User with email " + id + " not found","User"));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .map(this::convertToUserDto)
                .orElseThrow(()->new ResourceNotFoundException("User with email " + email + " not found","User"));
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .map(this::convertToUserDto)
                .orElseThrow(()->new ResourceNotFoundException("User with username " + username + " not found","User"));
    }

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        checkUserExist(request);
        return createUserDetails(request);
    }

    private void checkUserExist(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new ResourceAlreadyExistsException("User with email " + request.getEmail() + " already exists", "User");
        }
        else if(userRepository.existsByUsername(request.getUsername()))
        {
            throw new ResourceAlreadyExistsException("User with username " + request.getUsername() + " already exists", "User");
        }
    }

    private UserDto createUserDetails(CreateUserRequest request) {
        User user = modelMapper.map(request, User.class);
        user=userRepository.save(user);
        return convertToUserDto(user);
    }

    @Override
    public UserDto updateUser(UpdateUserRequest request) {
        checkUserExist(request);
        return UpdateUserDetails(request);
    }

    private UserDto UpdateUserDetails(UpdateUserRequest request) {
        User user = modelMapper.map(request, User.class);
        user=userRepository.save(user);
        return convertToUserDto(user);
    }

    private void checkUserExist(UpdateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new ResourceAlreadyExistsException("User with email " + request.getEmail() + " already exists", "User");
        }
        else if(userRepository.existsByUsername(request.getUsername()))
        {
            throw new ResourceAlreadyExistsException("User with username " + request.getUsername() + " already exists", "User");
        }
    }

    @Override
    public void deleteUser(Long id) {
         if(userRepository.existsById(id))
         {
             userRepository.deleteById(id);
         }
         else
         {
             throw new ResourceNotFoundException("User with id " + id + " not found", "User");
         }
    }
}
