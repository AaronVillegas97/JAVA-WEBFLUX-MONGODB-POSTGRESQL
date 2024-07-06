package com.gestorpedidos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("person")
public class PersonEntity {

    @Id
    private Integer id;
    @Column("first_name")
    String firstName;
    @Column("last_name")
    String lastName;
    @Column("document_number")
    String documentNumber;

    private Boolean status;
}
