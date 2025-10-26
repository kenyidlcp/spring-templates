package com.example.demo.service;

import com.example.demo.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Producto> catalogo() {
        return productos;
    }

    @Override
    public List<Producto> productosCategoria(String categoria) {
        return productos.stream()
                    .filter(p -> p.getCategoria().equals(categoria))
                    .toList();
    }

    @Override
    public Producto productoCodigo(Integer codigo) {
        return productos.stream()
                .filter(p -> p.getCodProducto() == codigo)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void altaProduct(Producto producto) {
        if (productoCodigo(producto.getCodProducto()) == null) {
            productos.add(producto);
        }
    }

    @Override
    public Producto eliminarProducto(Integer codigo) {
        Producto producto = productoCodigo(codigo);
        if (producto != null) {
            productos.removeIf(p -> p.getCodProducto() == codigo);
        }
        return producto;
    }

    @Override
    public Producto actualizarPrecio(Integer codigo, Double precio) {
        Producto producto = productoCodigo(codigo);
        if (producto != null) {
            producto.setPrecioUnitario(precio);
        }
        return producto;
    }
}
