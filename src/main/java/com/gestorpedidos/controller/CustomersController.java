package com.gestorpedidos.controller;

import com.gestorpedidos.dto.CustomerCreateDto;
import com.gestorpedidos.dto.CustomerDto;
import com.gestorpedidos.dto.PersonDto;
import com.gestorpedidos.entity.PedidoEntity;
import com.gestorpedidos.entity.PersonEntity;
import com.gestorpedidos.repository.PersonRepository;
import com.gestorpedidos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomersController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Flux<CustomerDto> listCustomers() {
        return customerService.listCustomers();
    }

    @PostMapping
    public Mono<CustomerCreateDto> saveCustomer(@RequestBody Mono<CustomerCreateDto> customerDtoMono) {
        return customerService.saveCustomer(customerDtoMono);
    }

    @GetMapping("/{id}")
    public Mono<CustomerDto> getCustomer(@PathVariable String id) {
        return customerService.getCustomer(id);
    }

    @PutMapping("/{id}")
    public Mono<CustomerDto> updateCustomer(@PathVariable String id, @RequestBody Mono<CustomerDto> customerDtoMono) {
        return customerService.updateCustomer(id, customerDtoMono);
    }

    @DeleteMapping("/{id}")
    public Mono<Boolean> deleteProduct(@PathVariable String id) {
        return customerService.deleteCustomer(id);
    }
}
