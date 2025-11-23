package com.example.demo.service;

import com.example.demo.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

    Flux<Producto> catalogo();
    Flux<Producto> productosCategoria(String categoria);
    Mono<Producto> productoCodigo(Integer codigo);
    Mono<Void> altaProduct(Producto producto);
    Mono<Producto> eliminarProducto(Integer codigo);
    Mono<Producto> actualizarPrecio(Integer codigo, Double precio);
}
