package com.example.demo.mapper;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
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
}
