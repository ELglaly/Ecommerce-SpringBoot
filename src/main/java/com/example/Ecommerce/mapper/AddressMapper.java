package com.example.Ecommerce.mapper;

import com.example.Ecommerce.model.dto.AddressDto;
import com.example.Ecommerce.model.entity.Address;
import com.example.Ecommerce.request.user.AddAddressRequest;
import com.example.Ecommerce.request.user.UpdateAddressRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements IAddressMapper {
    private final ModelMapper modelMapper;
    public AddressMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public AddressDto toDto(Address address) {
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public Address toEntityFromDto(AddressDto dto) {
        return modelMapper.map(dto, Address.class);
    }

    @Override
    public Address toEntityFromAddRequest(AddAddressRequest addRequest) {
        return null;
    }

    @Override
    public Address toEntityFromUpdateRequest(UpdateAddressRequest updateRequest) {
        return null;
    }
}
