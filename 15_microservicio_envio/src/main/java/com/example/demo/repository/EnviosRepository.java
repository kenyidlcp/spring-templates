package com.example.demo.repository;

import com.example.demo.model.Envio;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EnviosRepository extends ReactiveCrudRepository<Envio, Integer> {

    @Query("select * from envios where estado = 'Pendiente'")
    Flux<Envio> findByPendientes();
}
