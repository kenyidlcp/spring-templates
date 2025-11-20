package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(value = "productos")
public class Producto implements Persistable<Integer> {

    @Id
    @Column(value = "codProducto")
    private Integer codProducto;
    private String nombre;
    private String categoria;
    @Column(value = "precioUnitario")
    private Double precioUnitario;
    private Integer stock;

    @Transient
    private boolean nuevo;

    @Override
    public Integer getId() {
        return codProducto;
    }

    @Override
    public boolean isNew() {
        return nuevo;
    }
}
