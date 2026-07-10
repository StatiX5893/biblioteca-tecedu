package com.example.bibliotecatecedu.controller;

import com.example.bibliotecatecedu.dto.UserDto;
import com.example.bibliotecatecedu.service.CargaMasivaUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Carga Masiva Auth", description = "API para cargar múltiples usuarios de autenticación")
@RestController
@RequestMapping("/api/v1/auth/users")
public class CargaMasivaUserController {

    private final CargaMasivaUserService service;

    public CargaMasivaUserController(CargaMasivaUserService service) {
        this.service = service;
    }

    @Operation(
            summary = "Carga masiva de usuarios auth",
            description = "Permite registrar una lista de usuarios de autenticación con contraseña encriptada"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carga realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "La lista enviada está vacía")
    })
    @PostMapping("/masivo")
    public ResponseEntity<?> cargar(@RequestBody List<UserDto> users) {

        if (users == null || users.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista está vacía");
        }

        long inicio = System.currentTimeMillis();
        service.procesarCarga(users);
        long fin = System.currentTimeMillis();

        return ResponseEntity.ok(
                "Carga exitosa: " + users.size() + " usuarios auth en " + (fin - inicio) + " ms"
        );
    }
}