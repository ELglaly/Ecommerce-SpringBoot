package com.example.demo.mapper;

import com.example.demo.model.dto.AddressDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.PhoneNumber;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.User;
import com.example.demo.request.product.AddProductRequest;
import com.example.demo.request.product.UpdateProductRequest;
import com.example.demo.request.user.AddPhoneNumberRequest;
import com.example.demo.request.user.CreateUserRequest;
import com.example.demo.request.user.UpdateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class UserMapper implements IUserMapper
{
    private final IPhoneNumberMapper phoneNumberMapper;
    private final IAddressMapper addressMapper;
    private final ModelMapper modelMapper;

    public UserMapper(IPhoneNumberMapper phoneNumberMapper,IAddressMapper addressMapper,ModelMapper modelMapper)
    {
        this.phoneNumberMapper = phoneNumberMapper;
        this.addressMapper = addressMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto toDto(User user) {
        UserDto userDto= modelMapper.map(user,UserDto.class);
        Optional.ofNullable(user.getPhoneNumber())
                .ifPresent(phone -> {
                    userDto.setPhoneNumbers(phone.stream()
                            .map(phoneNumberMapper::toDto)
                            .collect(Collectors.toSet()));
                });

        Optional.ofNullable(user.getAddress())
                .ifPresent(address -> {
                    userDto.setAddress(addressMapper.toDto(address));
                });

        return userDto;

    }

    @Override
    public User toEntityFromAddRequest(CreateUserRequest addRequest) {
        User user = modelMapper.map(addRequest,User.class);
        Optional.ofNullable(addRequest.getPhoneNumber())
                .ifPresent(phone -> {
                    user.setPhoneNumber(phone.stream()
                            .map(phoneNumberMapper::toEntityFromAddRequest)
                            .collect(Collectors.toSet()));
                });
          Optional.ofNullable(addRequest.getAddress())
                    .ifPresent(address -> {
                        user.setAddress(addressMapper.toEntityFromAddRequest(address));
                    });
          return user;
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
