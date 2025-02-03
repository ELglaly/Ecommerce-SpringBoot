package com.example.demo.mapper;

import com.example.demo.model.dto.AddressDto;
import com.example.demo.model.entity.Address;

public interface IAddressMapper {

    AddressDto toDto(Address address);
}
