package com.example.demo.mapper;

import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.User;
import com.example.demo.request.product.AddProductRequest;
import com.example.demo.request.product.UpdateProductRequest;
import com.example.demo.request.user.CreateUserRequest;
import com.example.demo.request.user.UpdateUserRequest;
import org.springframework.stereotype.Component;


@Component
public class UserMapper implements IUserMapper
{
    private final IPhoneNumberMapper phoneNumberMapper;
    private final IAddressMapper addressMapper;

    public UserMapper(IPhoneNumberMapper phoneNumberMapper,IAddressMapper addressMapper)
    {
        this.phoneNumberMapper = phoneNumberMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto.Builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(addressMapper.toDto(user.getAddress()))
                .phoneNumbers(phoneNumberMapper.toDto(user.getPhoneNumber()))
                .build();
    }

    @Override
    public User toEntityFromAddRequest(CreateUserRequest addRequest) {
        return null;
    }

    @Override
    public User toEntityFromDto(UserDto dto) {
        return null;
    }

    @Override
    public User toEntityFromUpdateRequest(UpdateUserRequest updateRequest) {
        return null;
    }
}
