package com.example.demo.mapper;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;

public interface IUserMapper {
    UserDto toDto(User user);
}
