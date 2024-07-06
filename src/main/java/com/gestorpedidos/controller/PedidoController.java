package com.gestorpedidos.controller;

import com.gestorpedidos.dto.PedidoDto;
import com.gestorpedidos.entity.PedidoEntity;
import com.gestorpedidos.repository.PedidoRepository;
import com.gestorpedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public Flux<PedidoDto> listPedidos() {
        return pedidoService.listPedidos();
    }


    @GetMapping("{id}")
    public Mono<PedidoDto> getPedido(@PathVariable String id) {
        return pedidoService.getPedido(id);
    }


    @PostMapping
    public Mono<PedidoDto> savePedido(@RequestBody Mono<PedidoDto> pedidoDto) {
        return pedidoService.savePedido(pedidoDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Boolean> deleteProduct(@PathVariable String id) {
        return pedidoService.deletePedido(id);
    }

    @PutMapping("/{id}")
    public Mono<PedidoDto> updatePedido(@PathVariable String id, @RequestBody Mono<PedidoDto> pedidoDto) {
        return pedidoService.updatePedido(id, pedidoDto);
    }

}
