package com.example.demo.mapper;

public interface IUpdateRequestToEntityMapper<E, U> {
    E toEntityFromUpdateRequest(U updateRequest);
}
