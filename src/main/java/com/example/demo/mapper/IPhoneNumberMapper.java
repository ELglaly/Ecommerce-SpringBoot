package com.example.demo.mapper;

import com.example.demo.model.dto.PhoneNumberDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.PhoneNumber;

import java.util.Set;

public interface IPhoneNumberMapper extends IEntityToDtoMapper<Set<PhoneNumber>, Set<PhoneNumberDto>>,
        IDtoToEntityMapper<Set<PhoneNumber>, Set<PhoneNumberDto>>
        {
}
