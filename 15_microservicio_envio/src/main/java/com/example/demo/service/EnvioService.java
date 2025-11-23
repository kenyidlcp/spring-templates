package com.example.demo.service;

import com.example.demo.model.Envio;
import reactor.core.publisher.Flux;

public interface EnvioService {

    Flux<Envio> pendientes();
}
