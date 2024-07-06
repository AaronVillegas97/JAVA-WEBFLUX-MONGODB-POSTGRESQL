package com.gestorpedidos.service;

import com.gestorpedidos.dto.PedidoDto;
import com.gestorpedidos.repository.CustomerRespitory;
import com.gestorpedidos.repository.PedidoRepository;
import com.gestorpedidos.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CustomerService customerService;

    public Mono<PedidoDto> savePedido(Mono<PedidoDto> pedidoDto) {
        return pedidoDto.flatMap(dto -> {
            double totalPrice = dto.getProducts().stream()
                    .mapToDouble(product -> product.getPrice() * product.getQuantity())
                    .sum();
            dto.setTotalPrice(totalPrice);
            dto.setStatus(true);
            return pedidoRepository.insert(Utils.dtoToEntity(dto)).map(Utils::entityToDto);
        });

    }

    public Flux<PedidoDto> listPedidos() {
        return pedidoRepository.findByStatus(true)
                .flatMap(pedido ->
                        customerService.getCustomer(String.valueOf(pedido.getIdCustomer()))
                                .map(customer -> {
                                    PedidoDto pedidoDto = Utils.entityToDto(pedido);
                                    pedidoDto.setCustomer(customer);
                                    return pedidoDto;
                                })
                );
    }

    public Mono<PedidoDto> getPedido(String id) {
        return pedidoRepository.findById(id)
                .map(Utils::entityToDto)
                .switchIfEmpty(Mono.just(new PedidoDto()));
    }

    public Mono<Boolean> deletePedido(String id) {
        return pedidoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")))
                .flatMap(pedido -> {
                    pedido.setStatus(false);
                    return pedidoRepository.save(pedido);
                })
                .thenReturn(true);
    }

    public Mono<PedidoDto> updatePedido(String id, Mono<PedidoDto> pedidoDtoMono) {
        return pedidoRepository.findById(id)
                .flatMap(pedido ->
                        pedidoDtoMono.flatMap(pedidoDto -> {
                            pedido.setIdCustomer(pedidoDto.getIdCustomer());
                            pedido.setProducts(pedidoDto.getProducts());

                            double totalPrice = pedidoDto.getProducts().stream()
                                    .mapToDouble(product -> product.getPrice() * product.getQuantity())
                                    .sum();
                            pedido.setTotalPrice(totalPrice);
                            pedido.setStatus(pedidoDto.getStatus());


                            return pedidoRepository.save(pedido)
                                    .then(Mono.just(pedidoDto));
                        })
                );
    }
}
