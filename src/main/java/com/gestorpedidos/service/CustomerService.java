package com.gestorpedidos.service;

import com.gestorpedidos.dto.CustomerCreateDto;
import com.gestorpedidos.dto.CustomerDto;
import com.gestorpedidos.dto.PersonDto;
import com.gestorpedidos.entity.CustomerEntity;
import com.gestorpedidos.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gestorpedidos.repository.CustomerRespitory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    @Autowired
    private CustomerRespitory customerRespitory;

    @Autowired(required = true)
    private PersonService personService;

    public Flux<CustomerDto> listCustomers() {
        return customerRespitory.findAll()
                .flatMap(customer ->
                        personService.getPerson(customer.getId_person())
                                .map(person -> {
                                    PersonDto personDto = new PersonDto(person.getId(), person.getFirstName(), person.getLastName(), person.getDocumentNumber());
                                    return new CustomerDto(customer.getId(), customer.getId_person(), customer.getEmail(), customer.getStatus(), personDto);
                                })
                );
    }

    public Mono<CustomerCreateDto> saveCustomer(Mono<CustomerCreateDto> customerDto) {
        return customerDto
                .flatMap(customer -> personService.savePerson(customer.getPerson())
                        .flatMap(personDto -> {

                            customer.setPerson(personDto);
                            return customerRespitory.save(
                                            CustomerEntity.builder()
                                                    .id_person(personDto.getId())
                                                    .email(customer.getEmail())
                                                    .password(customer.getPassword())
                                                    .build()
                                    )
                                    .map(customerE -> new CustomerCreateDto(
                                            customerE.getId(),
                                            customerE.getId_person(),
                                            customerE.getEmail(),
                                            customerE.getPassword(),
                                            customerE.getStatus(),
                                            new PersonDto(personDto.getId(), personDto.getFirstName(), personDto.getLastName(), personDto.getDocumentNumber())
                                    ));
                        })
                );
    }

    public Mono<CustomerDto> getCustomer(String id){
        Integer nId = Integer.parseInt(id);
        return customerRespitory.findById(nId)
                .flatMap(customer ->
                        personService.getPerson(customer.getId_person())
                                .flatMap(person -> {
                                    PersonDto persondto = new PersonDto(person.getId(), person.getFirstName(), person.getLastName(), person.getDocumentNumber());
                                    return Mono.just(new CustomerDto(customer.getId(), customer.getId_person(), customer.getEmail(),customer.getStatus(), persondto));
                                })
                );
    }

    public Mono<CustomerDto> updateCustomer(String id, Mono<CustomerDto> customerDto){
        return  customerDto.flatMap(customer ->
        {
            return personService.updatePerson(customer.getId_person().toString(), customer.getPerson())
                    .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")))
                    .flatMap(personUpdated -> {
                        return customerRespitory.findById(Integer.parseInt(id)).flatMap(customerResult -> {
                            customerResult.setEmail(customer.getEmail());
                            customerResult.setStatus(customer.getStatus());
                            customerResult.setId_person(personUpdated.getId());
                            return customerRespitory.save(customerResult).map(responseSave -> {
                                CustomerDto updatedCustomerDto = new CustomerDto();
                                updatedCustomerDto.setId(responseSave.getId());
                                updatedCustomerDto.setEmail(responseSave.getEmail());
                                updatedCustomerDto.setPerson(personUpdated);
                                return updatedCustomerDto;
                            });
                        });

                            }
                    );
        });
    }

    public Mono<Boolean> deleteCustomer(String id) {
        return customerRespitory.findById(Integer.parseInt(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")))
                .flatMap(customer -> {
                    customer.setStatus(false);
                    return customerRespitory.save(customer);
                })
                .thenReturn(true);
    }

}
