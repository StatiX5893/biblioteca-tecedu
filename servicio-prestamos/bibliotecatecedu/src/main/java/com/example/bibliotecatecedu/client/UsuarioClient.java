package com.example.bibliotecatecedu.client;

import com.example.bibliotecatecedu.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "servicio-usuarios", url = "http://localhost:8083")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/username/{username}")
    UsuarioDTO obtenerUsuarioPorUsername(@PathVariable("username") String username);
}