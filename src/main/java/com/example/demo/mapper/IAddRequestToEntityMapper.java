package com.example.demo.mapper;

public interface IAddRequestToEntityMapper<E, A> {
    E toEntityFromAddRequest(A addRequest);
}
