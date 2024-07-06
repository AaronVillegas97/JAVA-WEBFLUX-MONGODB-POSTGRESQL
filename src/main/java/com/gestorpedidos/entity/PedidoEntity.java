package com.gestorpedidos.entity;

import com.gestorpedidos.dto.PedidoProductDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection  = "pedidos")
public class PedidoEntity {

    @Id
    private String id;
    private int idCustomer;
    private List<PedidoProductDto> products;
    private Boolean status;
    private double totalPrice;
}
