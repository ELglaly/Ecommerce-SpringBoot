package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.AddressDTO;
import com.example.ecommerce.entity.Address;
import com.example.ecommerce.request.user.AddAddressRequest;
import com.example.ecommerce.request.user.UpdateAddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * Mapper interface for converting between Address entity and AddressDTO, AddAddressRequest, and UpdateAddressRequest.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper
{
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDTO toDto(Address address);

    Address toEntity(AddAddressRequest request);

    Address toEntity(UpdateAddressRequest request);

    Address updateEntityFromRequest(UpdateAddressRequest request);
}
