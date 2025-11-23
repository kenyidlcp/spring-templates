package com.example.demo.service;

import com.example.demo.model.Elemento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ElementosServiceImpl implements ElementosService {

    String url1 = "http://servicio-producto1:8080";
    String url2 = "http://servicio-producto2:8090";

    @Autowired
    WebClient.Builder builder;

    @Override
    public Flux<Elemento> elementosprecioMax(double precioMax) {
        Flux<Elemento> flux1 = catalogo(url1, "tienda 1");
        Flux<Elemento> flux2 = catalogo(url2, "tienda 2");
        return Flux.merge(flux1, flux2)
                .filter(e -> e.getPrecioUnitario() <= precioMax);
    }

    private Flux<Elemento> catalogo(String url, String tienda) {
        WebClient webClient = builder.build();
        return webClient.get()
                        .uri(url + "/productos")
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToFlux(Elemento.class)
                        .map(p -> {
                            p.setTienda(tienda);
                            return p;
                        });
    }
}
