package com.example.demo.controller;

import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping(value = "productos",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Producto> productos() {
        return productoService.catalogo();
    }

    @GetMapping(value = "productos/{categoria}")
    public List<Producto> productosCategoria(@PathVariable("categoria") String categoria) {
        return productoService.productosCategoria(categoria);
    }

    @GetMapping(value = "producto")
    public Producto productoCodigo(@RequestParam("codigo") Integer codigo) {
        return productoService.productoCodigo(codigo);
    }

    @PostMapping(value = "alta", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void altaProducto(@RequestBody Producto producto) {
        productoService.altaProduct(producto);
    }

    @DeleteMapping(value = "eliminar")
    public Producto eliminarProducto(@RequestParam("codigo") Integer codigo) {
        return productoService.eliminarProducto(codigo);
    }

    @PutMapping(value = "actualizar")
    public Producto actualizarProducto(@RequestParam("codigo") Integer codigo, @RequestParam("precio") Double precio) {
        return productoService.actualizarPrecio(codigo, precio);

    }
}
