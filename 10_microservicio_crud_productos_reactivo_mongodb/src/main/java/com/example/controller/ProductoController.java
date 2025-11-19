package com.example.controller;

import com.example.model.Producto;
import com.example.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping(value = "/productos")
    public ResponseEntity<Flux<Producto>> productos() {
        return new ResponseEntity<>(productoService.catalogo(), HttpStatus.OK);
    }

    @GetMapping(value = "productos/por-categoria")
    //@GetMapping(value = "productos/por-categoria/{categoria}")
    public ResponseEntity<Flux<Producto>> productosCategoria(@RequestParam("categoria") String categoria) {
        return new ResponseEntity<>(productoService.productosCategoria(categoria), HttpStatus.OK);
    }

    @GetMapping(value = "productos/{codigo}")
    public ResponseEntity<Mono<Producto>> productoCodigo(@PathVariable("codigo") Integer codigo) {
        return new ResponseEntity<>(productoService.productoCodigo(codigo), HttpStatus.OK);
    }

    @PostMapping(value = "productos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Void>> altaProducto(@RequestBody Producto producto) {
        return new ResponseEntity<>(productoService.altaProduct(producto), HttpStatus.OK);
    }

    @DeleteMapping(value = "productos/{codigo}")
    public Mono<ResponseEntity<Producto>> eliminarProducto(@PathVariable("codigo") Integer codigo) {
        return productoService.eliminarProducto(codigo)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @PutMapping(value = "productos/{codigo}")
    public Mono<ResponseEntity<Producto>> actualizarProducto(@PathVariable("codigo") Integer codigo, @RequestParam("precio") Double precio) {
        return productoService.actualizarPrecio(codigo, precio)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
}
