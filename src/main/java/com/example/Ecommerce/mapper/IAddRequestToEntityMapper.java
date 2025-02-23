package com.example.Ecommerce.mapper;

public interface IAddRequestToEntityMapper<E, A> {
    E toEntityFromAddRequest(A addRequest);
}
