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
                    new Producto(100,"Azucar","Alimentación",1.10,20),
                    new Producto(101,"Leche","Alimentación",1.20,15),
                    new Producto(102,"Jabón","Limpieza",0.89,30),
                    new Producto(103,"Mesa","Hogar",125d,4),
                    new Producto(104,"Televisión","Hogar",650d,10),
                    new Producto(105,"Huevos","Alimentación",2.20,30),
                    new Producto(106,"Fregona","Limpieza",3.40,6),
                    new Producto(107,"Detergente","Limpieza",8.7,12)));

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
