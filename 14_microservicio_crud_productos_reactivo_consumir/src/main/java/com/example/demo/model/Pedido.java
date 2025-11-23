package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pedido {

    private Integer codigoProducto;
    private String nombre;
    private Integer unidades;
    private String direccion;
}
