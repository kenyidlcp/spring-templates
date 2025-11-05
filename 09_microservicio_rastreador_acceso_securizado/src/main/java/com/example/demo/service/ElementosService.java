package com.example.demo.service;

import com.example.demo.model.Elemento;
import reactor.core.publisher.Flux;

public interface ElementosService {

    Flux<Elemento> elementosprecioMax(double precioMax);
}
