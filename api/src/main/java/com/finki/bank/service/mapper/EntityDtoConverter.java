package com.finki.bank.service.mapper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EntityDtoConverter<D,E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);

}
