package com.example.demo.repository;

import com.example.demo.model.Usuario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<Usuario, String> {

    Mono<Usuario> findByUser(String user);
}
