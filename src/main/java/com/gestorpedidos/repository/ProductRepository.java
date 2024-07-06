package com.gestorpedidos.repository;

import com.gestorpedidos.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends R2dbcRepository<ProductEntity, Integer> {
    Flux<ProductEntity> findByStatus(Boolean status);
}
