package com.example.bibliotecatecedu.controller;

import com.example.bibliotecatecedu.dto.UsuarioDto;
import com.example.bibliotecatecedu.service.CargaMasivaUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Carga Masiva de Usuarios", description = "API para cargar múltiples usuarios en una sola solicitud")
@RestController
@RequestMapping("/api/v1/usuarios")
public class CargaMasivaUsuarioController {

    @Autowired
    private CargaMasivaUsuarioService service;

    @Operation(
            summary = "Carga masiva de usuarios",
            description = "Permite registrar una lista de usuarios usando procesamiento por lotes"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carga realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "La lista enviada está vacía")
    })
    @PostMapping("/masivo")
    public ResponseEntity<?> cargar(@RequestBody List<UsuarioDto> usuarios) {

        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista está vacía");
        }

        long inicio = System.currentTimeMillis();

        service.procesarCarga(usuarios);

        long fin = System.currentTimeMillis();

        return ResponseEntity.ok(
                "Carga exitosa: " + usuarios.size() + " usuarios en " + (fin - inicio) + " ms"
        );
    }
}