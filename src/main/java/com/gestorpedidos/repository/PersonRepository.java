package com.gestorpedidos.repository;

import com.gestorpedidos.entity.PersonEntity;
import com.gestorpedidos.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends R2dbcRepository<PersonEntity, Integer> {
    Flux<PersonEntity> findByStatus(Boolean status);
}
