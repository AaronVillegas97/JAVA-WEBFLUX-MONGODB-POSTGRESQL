package com.gestorpedidos.service;

import com.gestorpedidos.dto.PersonDto;
import com.gestorpedidos.entity.PersonEntity;
import com.gestorpedidos.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Mono<PersonDto> savePerson(PersonDto personDto) {
        return personRepository.save(
                        PersonEntity.builder()
                                .firstName(personDto.getFirstName())
                                .lastName(personDto.getLastName())
                                .documentNumber(personDto.getDocumentNumber())
                                .build())
                .map(response -> new PersonDto(response.getId(), response.getFirstName(), response.getLastName(), response.getDocumentNumber())
                );
    }

    public Mono<PersonDto> getPerson(Integer id) {
        return personRepository.findById(id)
                .map(customer ->
                        new PersonDto(
                                customer.getId(),
                                customer.getFirstName(),
                                customer.getLastName(),
                                customer.getDocumentNumber()))
                .switchIfEmpty(Mono.just(new PersonDto()));
    }

    public Mono<PersonDto> updatePerson(String id, PersonDto personDto) {
        PersonEntity personEntity = new PersonEntity();

        personEntity.setDocumentNumber(personDto.getDocumentNumber());
        personEntity.setFirstName(personDto.getFirstName());
        personEntity.setLastName(personDto.getLastName());
        PersonEntity.builder();
        Integer nId = Integer.parseInt(id);
        return personRepository.findById(nId).flatMap(person -> {
                            personEntity.setId(nId);
                            return personRepository.save(personEntity)
                                    .map(savedPersonEntity -> {
                                        PersonDto updatedPersonDto = new PersonDto();
                                        updatedPersonDto.setId(savedPersonEntity.getId());
                                        updatedPersonDto.setDocumentNumber(savedPersonEntity.getDocumentNumber());
                                        updatedPersonDto.setFirstName(savedPersonEntity.getFirstName());
                                        updatedPersonDto.setLastName(savedPersonEntity.getLastName());
                                        return updatedPersonDto;
                                    });
                        }
                );
    }

    public Mono<Boolean> deletePerson(String id) {
        return personRepository.findById(Integer.parseInt(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")))
                .flatMap(person -> {
                    person.setStatus(false);
                    return personRepository.save(person);
                })
                .thenReturn(true);
    }
}
