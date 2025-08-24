package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.PhoneNumberDTO;
import com.example.ecommerce.entity.PhoneNumber;
import com.example.ecommerce.request.user.AddPhoneNumberRequest;
import com.example.ecommerce.request.user.UpdatePhoneNumberRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * Mapper interface for converting between PhoneNumber entity and PhoneNumberDTO.
 * It also converts AddPhoneNumberRequest and UpdatePhoneNumberRequest to PhoneNumber entity.
 */
@Mapper(componentModel = "spring")
public interface PhoneNumberMapper {

    PhoneNumberMapper INSTANCE = Mappers.getMapper(PhoneNumberMapper.class);

    PhoneNumber toEntity(AddPhoneNumberRequest request);

    PhoneNumber toEntity(UpdatePhoneNumberRequest request);

    PhoneNumberDTO toDto(PhoneNumber phoneNumber);

    PhoneNumber toEntity(PhoneNumberDTO phoneNumberDTO);
}
