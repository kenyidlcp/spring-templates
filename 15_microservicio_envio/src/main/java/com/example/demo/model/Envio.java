package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(value = "envios")
public class Envio {

    @Id
    private Integer idEnvio;
    @JsonAlias(value = "nombre")
    private String producto;
    private LocalDateTime fecha;
    private String direccion;
    private String estado;
}
