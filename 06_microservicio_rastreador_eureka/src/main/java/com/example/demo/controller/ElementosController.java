package com.example.demo.controller;

import com.example.demo.model.Elemento;
import com.example.demo.service.ElementosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ElementosController {

    @Autowired
    ElementosService elementosService;

    @GetMapping(value = "/elementos/{precioMax}")
    //public ResponseEntity<Flux<Elemento>> getElemento(@RequestParam("precioMax") double precioMax) {
    public ResponseEntity<Flux<Elemento>> getElemento(@PathVariable("precioMax") double precioMax) {
        return ResponseEntity.ok(elementosService.elementosprecioMax(precioMax));
    }
}
