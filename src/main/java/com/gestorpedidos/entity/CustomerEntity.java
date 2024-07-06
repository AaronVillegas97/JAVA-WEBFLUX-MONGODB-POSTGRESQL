package com.gestorpedidos.entity;

import com.gestorpedidos.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("customer")
public class CustomerEntity {

    @Id
    private Integer id;
    private Integer id_person;
    private String email;
    private String password;
    private String auth_token;
    private Boolean status;
}
