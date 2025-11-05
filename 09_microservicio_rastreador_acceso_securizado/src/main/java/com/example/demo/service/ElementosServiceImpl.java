package com.example.demo.service;

import com.example.demo.model.Elemento;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ElementosServiceImpl implements ElementosService {

    String url1 = "http://localhost:8080";
    String url2 = "http://localhost:8090";

    @Value("${user}")
    String user;
    @Value("${pwd}")
    String pwd;

    @Override
    public Flux<Elemento> elementosprecioMax(double precioMax) {
        Flux<Elemento> flux1 = catalogo(url1, "tienda 1");
        Flux<Elemento> flux2 = catalogo(url2, "tienda 2");
        return Flux.merge(flux1, flux2)
                .filter(e -> e.getPrecioUnitario() <= precioMax);
    }

    private Flux<Elemento> catalogo(String url, String tienda) {
        WebClient webClient = WebClient.create(url);
        return webClient.get()
                .uri("/productos")
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + getEncoderBase64(user, pwd))
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
