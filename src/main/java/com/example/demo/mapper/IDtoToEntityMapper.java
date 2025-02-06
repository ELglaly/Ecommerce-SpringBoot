package com.example.demo.mapper;

public interface IDtoToEntityMapper<E, D> {
    E toEntityFromDto(D dto);
}
