package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Producto {
    private Integer codProducto;
    private String nombre;
    private String categoria;
    private Double precioUnitario;
    private Integer stock;
}
