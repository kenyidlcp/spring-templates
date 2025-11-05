package com.example.demo.runner;


import com.example.demo.model.Producto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TestRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        WebClient webClient = WebClient.create("http://localhost:8080");

        /*Alta de Producto*/
        webClient.post()
                .uri("/productos")
                .body(Mono.just(new Producto(200, "prueba", "categoria", 5.0, 20)), Producto.class)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnTerminate(() -> System.out.println("Dato de alta el producto"))
                .block();


        /*Consulta de Producto*/
        Flux<Producto> producto = webClient.get()
                                    .uri("/productos")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .retrieve()
                                    .bodyToFlux(Producto.class);
        producto.subscribe(System.out::println);

        /*Consulta de producto por código*/

        webClient = WebClient.create("http://localhost:8080");
        Mono<Producto> productoMono = webClient.get()
                .uri("/productos/102")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Producto.class);

        productoMono.subscribe(System.out::println);
        productoMono.switchIfEmpty(Mono.just(new Producto()).map( p -> {
            System.out.println("No se ha encontrado Producto");
            return p;
        })).block();

        webClient.delete()
                .uri("/productos/102")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, t -> {
                    System.out.println("No se encontró el Producto para eliminar");
                    return Mono.empty();
                })
                .bodyToMono(Producto.class)
                .subscribe(System.out::println);
    }
}
