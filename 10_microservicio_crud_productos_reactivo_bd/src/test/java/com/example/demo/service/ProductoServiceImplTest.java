package com.example.demo.service;

import com.example.demo.model.Producto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ProductoServiceImplTest {

    @Autowired
    ProductoService productoService;

    @Test
    @Order(4)
    void productosCategoria() {
        StepVerifier.create(productoService.productosCategoria("Alimentación"))
                .expectNextMatches(p -> p.getNombre().equals("Azucar"))
                .expectNextMatches(p -> p.getNombre().equals("Leche"))
                .expectNextMatches(p -> p.getNombre().equals("Huevos"))
                .verifyComplete();
    }

    @Test
    @Order(3)
    void altaProduct() {
        Producto producto = new Producto(250, "pTest", "cat1", 10d, 2);
        StepVerifier.create(productoService.altaProduct(producto))
                .expectComplete()
                .verify();
    }

    @Test
    @Order(2)
    void eliminarProducto() {
        StepVerifier.create(productoService.eliminarProducto(103))
                .expectNextMatches(p -> p.getNombre().equals("Mesa"))
                .verifyComplete();
    }

    @Test
    @Order(1)
    void testCatalogo() {
        StepVerifier.create(productoService.catalogo())
                .expectNextCount(8)
                .verifyComplete();
    }
}