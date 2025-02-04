package com.example.demo.mapper;

import com.example.demo.model.dto.AddressDto;
import com.example.demo.model.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements IAddressMapper {
    @Override
    public AddressDto toDto(Address address) {
        return null;
    }
}
