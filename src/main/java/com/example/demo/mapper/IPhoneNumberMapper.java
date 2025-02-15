package com.example.demo.mapper;

import com.example.demo.model.dto.PhoneNumberDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.PhoneNumber;
import com.example.demo.request.user.AddPhoneNumberRequest;
import com.example.demo.request.user.UpdatePhoneNumberRequest;

import java.util.Set;

public interface IPhoneNumberMapper extends IEntityToDtoMapper<PhoneNumber, PhoneNumberDto>,
        IDtoToEntityMapper< PhoneNumber, PhoneNumberDto>,
        IAddRequestToEntityMapper<PhoneNumber, AddPhoneNumberRequest>,
        IUpdateRequestToEntityMapper<PhoneNumber, UpdatePhoneNumberRequest>
{

}
