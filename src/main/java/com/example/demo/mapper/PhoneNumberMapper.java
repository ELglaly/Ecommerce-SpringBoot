package com.example.demo.mapper;

import com.example.demo.model.dto.PhoneNumberDto;
import com.example.demo.model.entity.PhoneNumber;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PhoneNumberMapper implements IPhoneNumberMapper {
    @Override
    public Set<PhoneNumberDto> toDto(Set<PhoneNumber> phoneNumber) {
        return null;
    }
}
