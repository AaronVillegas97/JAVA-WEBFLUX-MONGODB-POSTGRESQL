package com.gestorpedidos.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateDto extends  CustomerDto{
    private String password;

    public CustomerCreateDto(Integer id, Integer id_person, String email, String password, Boolean status, PersonDto personDto) {
        super(id, id_person, email, status, personDto); // Llama al constructor de la superclase CustomerDto
        this.password = password;
    }
}
