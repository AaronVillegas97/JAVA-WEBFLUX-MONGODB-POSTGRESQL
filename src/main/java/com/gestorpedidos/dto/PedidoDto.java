package com.gestorpedidos.dto;

import com.gestorpedidos.entity.PedidoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {
     private String id;
     private int idCustomer;
     private CustomerDto customer;
     private List<PedidoProductDto> products;
     private Boolean Status;
     private double totalPrice;
}


