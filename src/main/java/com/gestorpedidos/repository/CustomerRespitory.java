package com.gestorpedidos.repository;

import com.gestorpedidos.entity.CustomerEntity;
import com.gestorpedidos.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CustomerRespitory extends R2dbcRepository<CustomerEntity, Integer>{
    Flux<CustomerEntity> findByStatus(Boolean status);
}
