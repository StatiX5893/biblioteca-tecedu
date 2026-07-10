package com.example.bibliotecatecedu.controller;

import com.example.bibliotecatecedu.dto.PrestamoDto;
import com.example.bibliotecatecedu.service.CargaMasivaPrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Carga Masiva de Préstamos", description = "API para cargar múltiples préstamos en una sola solicitud")
@RestController
@RequestMapping("/api/v1/prestamos")
public class CargaMasivaPrestamoController {

    @Autowired
    private CargaMasivaPrestamoService service;

    @Operation(
            summary = "Carga masiva de préstamos",
            description = "Permite registrar una lista de préstamos usando procesamiento por lotes"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carga realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "La lista enviada está vacía")
    })
    @PostMapping("/masivo")
    public ResponseEntity<?> cargar(@RequestBody List<PrestamoDto> prestamos) {

        if (prestamos == null || prestamos.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista está vacía");
        }

        long inicio = System.currentTimeMillis();

        service.procesarCarga(prestamos);

        long fin = System.currentTimeMillis();

        return ResponseEntity.ok(
                "Carga exitosa: " + prestamos.size() + " préstamos en " + (fin - inicio) + " ms"
        );
    }
}