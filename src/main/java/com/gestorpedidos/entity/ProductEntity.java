package com.gestorpedidos.entity;

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
@Table("products")
public class ProductEntity {

    @Id
    private Integer id;
    String name;
    Double price;
    Integer stock;
    Boolean status;
}
