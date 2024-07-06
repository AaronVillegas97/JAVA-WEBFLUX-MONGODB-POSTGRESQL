package com.gestorpedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Integer id;
    private Integer id_person;
    public String email;
    public Boolean status;
    PersonDto person;
}

