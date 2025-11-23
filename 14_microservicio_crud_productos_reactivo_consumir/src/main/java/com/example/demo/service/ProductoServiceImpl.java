package com.example.demo.service;

import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    ProductosRepository productosRepository;

    @Override
    public Flux<Producto> catalogo() {
        return productosRepository.findAll();
                //.delayElements(Duration.ofSeconds(2));
    }

    @Override
    public Flux<Producto> productosCategoria(String categoria) {
        return productosRepository.findByCategoria(categoria);
    }

    @Override
    public Mono<Producto> productoCodigo(Integer codigo) {
        return productosRepository.findById(codigo);
                //.switchIfEmpty(Mono.just(new Producto()));
    }

    @Override
    public Mono<Void> altaProduct(Producto producto) {
        return productoCodigo(producto.getCodProducto())
                .switchIfEmpty(Mono.just(producto)
                        .flatMap(p -> productosRepository.save(p))
                ).then();
    }

    @Override
    public Mono<Producto> eliminarProducto(Integer codigo) {
        return productoCodigo(codigo)
                .flatMap(p ->
                        productosRepository.deleteById(codigo).then(Mono.just(p))
                );
                //.switchIfEmpty(null);
    }

    @Override
    public Mono<Producto> actualizarPrecio(Integer codigo, Double precio) {
        return productoCodigo(codigo)
                .flatMap(p -> {
                    p.setPrecioUnitario(precio);
                    return productosRepository.save(p);
                });
    }

    @KafkaListener(topics = "pedidosTopic", groupId = "myGroup1")
    public void gestionPedido(Pedido pedido) {
        productoCodigo(pedido.getCodigoProducto())
                .flatMap(prod -> {
                    prod.setStock(prod.getStock() - pedido.getUnidades());
                    return productosRepository.save(prod);
                }).subscribe();
    }
}
