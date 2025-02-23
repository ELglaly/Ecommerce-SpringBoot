package com.example.Ecommerce.mapper;

public interface IDtoToEntityMapper<E, D> {
    E toEntityFromDto(D dto);
}
