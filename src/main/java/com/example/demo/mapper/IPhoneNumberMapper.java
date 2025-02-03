package com.example.demo.mapper;

import com.example.demo.model.dto.PhoneNumberDto;
import com.example.demo.model.entity.PhoneNumber;

import java.util.Set;

public interface IPhoneNumberMapper {

    Set<PhoneNumberDto> toDto(Set<PhoneNumber> phoneNumber);
}
