package com.example.Ecommerce.mapper;

public interface IUpdateRequestToEntityMapper<E, U> {
    E toEntityFromUpdateRequest(U updateRequest);
}
