package com.example.demo.controller;

import com.example.demo.model.Envio;
import com.example.demo.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class EnviosController {

    @Autowired
    EnvioService envioService;

    public ResponseEntity<Flux<Envio>> enviosPendientes() {
        return new ResponseEntity<>(envioService.pendientes(), HttpStatus.OK);
    }
}
