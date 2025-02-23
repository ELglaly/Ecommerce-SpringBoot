package com.example.Ecommerce.mapper;

import com.example.Ecommerce.model.dto.UserDto;
import com.example.Ecommerce.model.entity.User;
import com.example.Ecommerce.request.user.CreateUserRequest;
import com.example.Ecommerce.request.user.UpdateUserRequest;

public interface IUserMapper extends IEntityToDtoMapper<User, UserDto>,
IDtoToEntityMapper<User, UserDto> ,
IUpdateRequestToEntityMapper<User, UpdateUserRequest>,
        IAddRequestToEntityMapper<User, CreateUserRequest>  {
}
