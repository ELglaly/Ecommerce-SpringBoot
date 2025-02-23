package com.example.Ecommerce.mapper;

import com.example.Ecommerce.model.dto.PhoneNumberDto;
import com.example.Ecommerce.model.entity.PhoneNumber;
import com.example.Ecommerce.request.user.AddPhoneNumberRequest;
import com.example.Ecommerce.request.user.UpdatePhoneNumberRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberMapper implements IPhoneNumberMapper {
    private final ModelMapper modelMapper;
    public PhoneNumberMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PhoneNumberDto toDto(PhoneNumber phoneNumber) {
        return modelMapper.map(phoneNumber,PhoneNumberDto.class);
    }

    @Override
    public PhoneNumber toEntityFromDto( PhoneNumberDto dto) {
        return modelMapper.map(dto,PhoneNumber.class);
    }

    @Override
    public  PhoneNumber toEntityFromAddRequest(AddPhoneNumberRequest addRequest) {
        return modelMapper.map(addRequest,PhoneNumber.class);
    }

    @Override
    public PhoneNumber toEntityFromUpdateRequest(UpdatePhoneNumberRequest updateRequest) {
        return modelMapper.map(updateRequest,PhoneNumber.class);
    }
}
