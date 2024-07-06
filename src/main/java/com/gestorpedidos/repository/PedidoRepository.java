package com.gestorpedidos.repository;


import com.gestorpedidos.dto.PedidoDto;
import com.gestorpedidos.entity.PedidoEntity;
import com.gestorpedidos.entity.ProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PedidoRepository extends ReactiveMongoRepository<PedidoEntity, String> {
    Flux<PedidoEntity> findByStatus(Boolean status);
}
