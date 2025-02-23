package com.example.Ecommerce.mapper;

import com.example.Ecommerce.model.dto.PhoneNumberDto;
import com.example.Ecommerce.model.entity.PhoneNumber;
import com.example.Ecommerce.request.user.AddPhoneNumberRequest;
import com.example.Ecommerce.request.user.UpdatePhoneNumberRequest;

public interface IPhoneNumberMapper extends IEntityToDtoMapper<PhoneNumber, PhoneNumberDto>,
        IDtoToEntityMapper< PhoneNumber, PhoneNumberDto>,
        IAddRequestToEntityMapper<PhoneNumber, AddPhoneNumberRequest>,
        IUpdateRequestToEntityMapper<PhoneNumber, UpdatePhoneNumberRequest>
{

}
