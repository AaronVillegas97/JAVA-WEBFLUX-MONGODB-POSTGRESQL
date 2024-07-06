package com.gestorpedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  PedidoProductDto extends  ProductDto{
    private int quantity;
}
