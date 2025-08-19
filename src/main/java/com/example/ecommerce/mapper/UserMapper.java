package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entity.user.User;
import com.example.ecommerce.request.user.CreateUserRequest;
import com.example.ecommerce.request.user.UpdateUserRequest;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between User entity and UserDTO.
 * It uses MapStruct to generate the implementation at compile time.
 */

@Mapper(componentModel = "spring")
public interface UserMapper{

    UserMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);

    UserDTO toDto(User user);

    User toEntity(CreateUserRequest createUserRequest);

    User toEntity(UpdateUserRequest updateUserRequest);

    UserDTO toUserDTOFromUpdateRequest(UpdateUserRequest updateUserRequest);

    UserDTO toUserDTOFromCreateRequest(CreateUserRequest createUserRequest);

    User toEntity(UserDTO userDTO);
}
