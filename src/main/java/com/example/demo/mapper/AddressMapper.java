package com.example.demo.mapper;

import com.example.demo.model.dto.AddressDto;
import com.example.demo.model.entity.Address;
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
}
