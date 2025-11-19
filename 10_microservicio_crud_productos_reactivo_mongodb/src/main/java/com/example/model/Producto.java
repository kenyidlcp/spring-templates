package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "productos")
public class Producto {

    private Integer id;
    private String nombre;
    private String categoria;
    private Double precioUnitario;
    private Integer stock;
}
