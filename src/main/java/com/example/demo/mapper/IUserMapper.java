package com.example.demo.mapper;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.User;
import com.example.demo.request.user.CreateUserRequest;
import com.example.demo.request.user.UpdateUserRequest;
import com.example.demo.request.user.UpdateUserRequest;

public interface IUserMapper extends IEntityToDtoMapper<User, UserDto>,
IDtoToEntityMapper<User, UserDto> ,
IUpdateRequestToEntityMapper<User, UpdateUserRequest>,
        IAddRequestToEntityMapper<User, CreateUserRequest>  {
}
