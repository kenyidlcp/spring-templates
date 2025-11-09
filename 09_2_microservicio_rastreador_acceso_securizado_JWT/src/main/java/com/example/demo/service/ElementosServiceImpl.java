package com.example.demo.service;

import com.example.demo.model.Credentials;
import com.example.demo.model.Elemento;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ElementosServiceImpl implements ElementosService {

    String url1 = "http://localhost:7000";
    String url2 = "http://localhost:7010";

    @Value("${user}")
    String user;
    @Value("${pwd}")
    String pwd;

    String token1,token2;

    @PostConstruct
    public void init() {
        Credentials credentials1 = new Credentials(user, pwd);
        Credentials credentials2 = new Credentials(user, pwd);
        loadToken(url1, credentials1)
                .subscribe(s -> token1 = s);
        loadToken(url2, credentials2)
                .subscribe(s -> token2 = s);
    }

    private Mono<String> loadToken(String url, Credentials credentials) {
        return WebClient.create(url)
                .post()
                .uri("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(credentials)
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Flux<Elemento> elementosprecioMax(double precioMax) {
        Flux<Elemento> flux1 = catalogo(url1, "tienda 1", token1);
        Flux<Elemento> flux2 = catalogo(url2, "tienda 2", token2);
        return Flux.merge(flux1, flux2)
                .filter(e -> e.getPrecioUnitario() <= precioMax);
    }

    private Flux<Elemento> catalogo(String url, String tienda, String token) {
        WebClient webClient = WebClient.create(url);
        return webClient.get()
                .uri("/productos")
                .accept(MediaType.APPLICATION_JSON)
                //.header(HttpHeaders.AUTHORIZATION, "Basic " + getEncoderBase64(user, pwd))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToFlux(Elemento.class)
                .map(p -> {
                    p.setTienda(tienda);
                    return p;
                });
    }

    private String getEncoderBase64(String user, String pwd) {
        String credential = user.concat(":").concat(pwd);
        return Base64.getEncoder().encodeToString(credential.getBytes(StandardCharsets.UTF_8));
    }
}
