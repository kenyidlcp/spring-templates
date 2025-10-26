package com.example.demo.service;

import com.example.demo.model.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> catalogo();
    List<Producto> productosCategoria(String categoria);
    Producto productoCodigo(Integer codigo);
    void altaProduct(Producto producto);
    Producto eliminarProducto(Integer codigo);
    Producto actualizarPrecio(Integer codigo, Double precio);
}
