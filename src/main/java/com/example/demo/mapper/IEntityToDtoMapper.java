package com.example.demo.mapper;

public interface IEntityToDtoMapper<E, D> {
    D toDto(E entity);
}

