package com.example.demo.mapper;

import com.example.demo.model.dto.PhoneNumberDto;
import com.example.demo.model.entity.PhoneNumber;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PhoneNumberMapper implements IPhoneNumberMapper {
    private final ModelMapper modelMapper;
    public PhoneNumberMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<PhoneNumberDto> toDto(Set<PhoneNumber> phoneNumber) {
        return phoneNumber.stream()
                .map(phoneNumber1 -> modelMapper.map(phoneNumber1,PhoneNumberDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<PhoneNumber> toEntityFromDto(Set<PhoneNumberDto> dto) {
        return dto.stream()
                .map(phoneNumber1 -> modelMapper.map(dto,PhoneNumber.class))
                .collect(Collectors.toSet());
    }
}
