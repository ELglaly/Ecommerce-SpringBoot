package com.example.Ecommerce.mapper;

import com.example.Ecommerce.model.dto.AddressDto;
import com.example.Ecommerce.model.entity.Address;
import com.example.Ecommerce.request.user.AddAddressRequest;
import com.example.Ecommerce.request.user.UpdateAddressRequest;

public interface IAddressMapper extends IEntityToDtoMapper<Address,AddressDto>,
        IDtoToEntityMapper<Address,AddressDto>,
        IAddRequestToEntityMapper<Address, AddAddressRequest>,
        IUpdateRequestToEntityMapper<Address, UpdateAddressRequest>

{
}
