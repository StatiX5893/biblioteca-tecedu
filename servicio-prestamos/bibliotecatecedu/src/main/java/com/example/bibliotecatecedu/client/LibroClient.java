package com.example.bibliotecatecedu.client;

import com.example.bibliotecatecedu.dto.LibroDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "servicio-libros", url = "http://localhost:8081")
public interface LibroClient {

    @GetMapping("/api/v1/libros/{id}")
    LibroDTO obtenerLibroPorId(@PathVariable("id") Long id);
}