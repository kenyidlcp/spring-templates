package com.example.demo.service;

import com.example.demo.model.Producto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private static List<Producto> productos =
            new ArrayList<>(List.of(
                    new Producto(200,"Azucar","Alimentación",1.10,20),
                    new Producto(201,"Pan","Alimentación",1.30,30),
                    new Producto(202,"Shampoo","Limpieza",2.0,20),
                    new Producto(203,"Sofá","Hogar",80.0,40),
                    new Producto(204,"Jarrón","Hogar",10.0,15),
                    new Producto(205,"Huevos","Alimentación",2.20,30),
                    new Producto(206,"Fregona","Limpieza",3.40,6),
                    new Producto(207,"Suavizante","Limpieza",12.7,20)));

    @Override
    public Flux<Producto> catalogo() {
        return Flux.fromIterable(productos);
                //.delayElements(Duration.ofSeconds(2));
    }

    @Override
    public Flux<Producto> productosCategoria(String categoria) {
        return catalogo().filter(p -> p.getCategoria().equals(categoria));
    }

    @Override
    public Mono<Producto> productoCodigo(Integer codigo) {
        return catalogo()
                .filter(p -> p.getCodProducto().equals(codigo))
                .next();
                //.switchIfEmpty(Mono.just(new Producto()));
    }

    @Override
    public Mono<Void> altaProduct(Producto producto) {
        return productoCodigo(producto.getCodProducto())
                .switchIfEmpty(Mono.just(producto).map(p -> {
                    productos.add(p);
                    return p;
                }))
                .then();
    }

    @Override
    public Mono<Producto> eliminarProducto(Integer codigo) {
        return productoCodigo(codigo)
                .map(p -> {
                    productos.removeIf(r -> r.getCodProducto() == codigo);
                    return p;
                });
                //.switchIfEmpty(null);
    }

    @Override
    public Mono<Producto> actualizarPrecio(Integer codigo, Double precio) {
        return productoCodigo(codigo)
                .map(p -> {
                    p.setPrecioUnitario(precio);
                    return p;
                });
    }
}
