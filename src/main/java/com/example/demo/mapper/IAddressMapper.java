package com.example.demo.mapper;

import com.example.demo.model.dto.AddressDto;
import com.example.demo.model.entity.Address;
import org.apache.catalina.mapper.Mapper;

public interface IAddressMapper extends IEntityToDtoMapper<Address,AddressDto>,
        IDtoToEntityMapper<Address,AddressDto>

{
}
