package com.example.Ecommerce.mapper;

public interface IEntityToDtoMapper<E, D> {
    D toDto(E entity);
}

