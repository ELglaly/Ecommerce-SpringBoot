package com.example.demo.mapper;

import com.example.demo.model.dto.AddressDto;
import com.example.demo.model.entity.Address;
import com.example.demo.request.user.AddAddressRequest;
import com.example.demo.request.user.UpdateAddressRequest;
import org.apache.catalina.mapper.Mapper;

public interface IAddressMapper extends IEntityToDtoMapper<Address,AddressDto>,
        IDtoToEntityMapper<Address,AddressDto>,
        IAddRequestToEntityMapper<Address, AddAddressRequest>,
        IUpdateRequestToEntityMapper<Address, UpdateAddressRequest>

{
}
