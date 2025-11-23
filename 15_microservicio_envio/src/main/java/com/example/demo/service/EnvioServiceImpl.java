package com.example.demo.service;

import com.example.demo.model.Envio;
import com.example.demo.repository.EnviosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service
public class EnvioServiceImpl implements EnvioService {

    @Autowired
    EnviosRepository enviosRepository;

    @Override
    public Flux<Envio> pendientes() {
        return enviosRepository.findByPendientes();
    }

    @KafkaListener(topics = "pedidosTopic", groupId = "myGroup2")
    public void gestionEnvios(Envio envio) {
        envio.setFecha(LocalDateTime.now());
        envio.setEstado("Pendiente");
        enviosRepository.save(envio).subscribe();
    }
}
