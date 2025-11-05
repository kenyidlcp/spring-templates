package com.example.demo.controller;

import com.example.demo.model.Elemento;
import com.example.demo.service.ElementosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

@Controller
public class ElementosController {

    @Autowired
    ElementosService elementosService;

    @GetMapping(value = "buscar")
    public String buscar(@RequestParam("precio") double precioMax, Model model) {
        ReactiveDataDriverContextVariable reactive
                = new ReactiveDataDriverContextVariable(elementosService.elementosPorPrecio(precioMax), 1);
        model.addAttribute("resultado", reactive);
        return "listado";
    }

    @GetMapping(value = "/")
    public String inicio() {
        return "inicio";
    }
}
