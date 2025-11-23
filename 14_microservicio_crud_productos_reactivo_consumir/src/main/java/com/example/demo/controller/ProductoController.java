package com.example.demo.controller;

import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@CrossOrigin("*")
//@RestController
@Configuration
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @Bean
    public RouterFunction<ServerResponse> response() {
        return RouterFunctions.route(RequestPredicates.GET("productos"),
                        req -> ServerResponse.ok() //BodyBuilder
                        .body(productoService.catalogo(), Producto.class)) // Mono<ServerResponse>
                .andRoute(RequestPredicates.GET("productos/por-categoria"),
                        req -> ServerResponse.ok() //BodyBuilder
                        .body(productoService.productosCategoria(req.queryParam("categoria").get()),
                                Producto.class))
                .andRoute(RequestPredicates.GET("productos/{codigo}"),
                        req -> ServerResponse.ok()
                        .body(productoService.productoCodigo(Integer.parseInt(req.pathVariable("codigo"))),
                                Producto.class))
                .andRoute(RequestPredicates.POST("productos"),
                        req -> req.bodyToMono(Producto.class)//Mono<Producto>
                        .flatMap(p -> {
                            p.setNuevo(true);
                            return productoService.altaProduct(p);
                        })// Mono<Void
                        .flatMap(v -> ServerResponse.ok().build())) // Body Builder
                .andRoute(RequestPredicates.DELETE("productos"),
                        req -> productoService.eliminarProducto(req.queryParam("codigo").map(Integer::parseInt).get())
                        .flatMap(p -> ServerResponse.ok()
                                .bodyValue(p))
                        .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)
                                .build()))
                .andRoute(RequestPredicates.PUT("productos/{codigo}"),
                        req -> productoService.actualizarPrecio(Integer.parseInt(req.pathVariable("codigo")),
                                req.queryParam("precio").map(Double::parseDouble).get())
                                .flatMap(p -> ServerResponse.ok()
                                        .bodyValue(p)));

    }

    @Bean
    CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    /*@GetMapping(value = "productos")
    public ResponseEntity<Flux<Producto>> productos() {
        return new ResponseEntity<>(productoService.catalogo(), HttpStatus.OK);
    }

    @GetMapping(value = "productos/por-categoria")
    //@GetMapping(value = "productos/por-categoria/{categoria}")
    public ResponseEntity<Flux<Producto>> productosCategoria(@RequestParam("categoria") String categoria) {
        return new ResponseEntity<>(productoService.productosCategoria(categoria), HttpStatus.OK);
    }

    @GetMapping(value = "productos/{codigo}")
    public ResponseEntity<Mono<Producto>> productoCodigo(@PathVariable("codigo") Integer codigo) {
        return new ResponseEntity<>(productoService.productoCodigo(codigo), HttpStatus.OK);
    }

    @PostMapping(value = "productos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Void>> altaProducto(@RequestBody Producto producto) {
        producto.setNuevo(true);
        return new ResponseEntity<>(productoService.altaProduct(producto), HttpStatus.OK);
    }

    @DeleteMapping(value = "productos/{codigo}")
    public Mono<ResponseEntity<Producto>> eliminarProducto(@PathVariable("codigo") Integer codigo) {
        return productoService.eliminarProducto(codigo)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @PutMapping(value = "productos/{codigo}")
    public Mono<ResponseEntity<Producto>> actualizarProducto(@PathVariable("codigo") Integer codigo, @RequestParam("precio") Double precio) {
        return productoService.actualizarPrecio(codigo, precio)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }*/
}
