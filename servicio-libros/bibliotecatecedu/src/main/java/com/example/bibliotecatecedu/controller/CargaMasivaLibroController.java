package com.example.bibliotecatecedu.controller;

import com.example.bibliotecatecedu.dto.LibroDto;
import com.example.bibliotecatecedu.service.CargaMasivaLibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Carga Masiva de Libros", description = "API para cargar múltiples libros en una sola solicitud")
@RestController
@RequestMapping("/api/v1/libros")
public class CargaMasivaLibroController {

    @Autowired
    private CargaMasivaLibroService service;

    @Operation(
            summary = "Carga masiva de libros",
            description = "Permite registrar una lista de libros usando procesamiento por lotes"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carga realizada correctamente"),
            @ApiResponse(responseCode = "400", description = "La lista enviada está vacía")
    })
    @PostMapping("/masivo")
    public ResponseEntity<?> cargar(@RequestBody List<LibroDto> libros) {

        if (libros == null || libros.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista está vacía");
        }

        long inicio = System.currentTimeMillis();

        service.procesarCarga(libros);

        long fin = System.currentTimeMillis();

        return ResponseEntity.ok(
                "Carga exitosa: " + libros.size() + " registros en " + (fin - inicio) + " ms"
        );
    }
}