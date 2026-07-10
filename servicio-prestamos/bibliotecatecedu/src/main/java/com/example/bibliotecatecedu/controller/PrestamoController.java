package com.example.bibliotecatecedu.controller;

import com.example.bibliotecatecedu.model.Prestamo;
import com.example.bibliotecatecedu.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Préstamos", description = "API para la gestión de préstamos de libros")
@RestController
@RequestMapping("/api/v1/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Operation(
            summary = "Listar préstamos",
            description = "Obtiene todos los préstamos registrados en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<Prestamo>> listarPrestamos() {
        return new ResponseEntity<>(prestamoService.getPrestamos(), HttpStatus.OK);
    }

    @Operation(
            summary = "Crear préstamo",
            description = "Registra un nuevo préstamo validando previamente que el libro exista en servicio-libros"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Préstamo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "No se pudo crear el préstamo porque el libro no existe o el servicio no responde")
    })
    @PostMapping
    public ResponseEntity<Prestamo> guardarPrestamo(@RequestBody Prestamo prestamo) {

        Prestamo nuevoPrestamo = prestamoService.savePrestamo(prestamo);

        if (nuevoPrestamo != null) {
            return new ResponseEntity<>(nuevoPrestamo, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Listar préstamos con datos del libro",
            description = "Obtiene los préstamos registrados junto con la información del libro asociado mediante OpenFeign"
    )
    @ApiResponse(responseCode = "200", description = "Listado con libros obtenido correctamente")
    @GetMapping("/con-libros")
    public ResponseEntity<?> obtenerPrestamosConLibros() {
        return ResponseEntity.ok(prestamoService.getPrestamosConLibros());
    }

    @Operation(
            summary = "Listar detalle completo de préstamos",
            description = "Obtiene los préstamos junto con información del libro y del usuario solicitante mediante OpenFeign"
    )
    @ApiResponse(responseCode = "200", description = "Detalle de préstamos obtenido correctamente")
    @GetMapping("/detalle")
    public ResponseEntity<?> obtenerDetallePrestamos() {
        return ResponseEntity.ok(prestamoService.getPrestamosDetalle());
    }

    @Operation(
            summary = "Buscar préstamo por ID",
            description = "Obtiene un préstamo específico mediante su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPrestamo(@PathVariable("id") Long id) {

        Prestamo prestamo = prestamoService.getPrestamoId(id);

        if (prestamo != null) {
            return new ResponseEntity<>(prestamo, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Actualizar préstamo",
            description = "Actualiza los datos de un préstamo existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> actualizarPrestamo(
            @PathVariable("id") Long id,
            @RequestBody Prestamo prestamo) {

        Prestamo existente = prestamoService.getPrestamoId(id);

        if (existente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        prestamo.setId(id);

        Prestamo prestamoActualizado = prestamoService.updatePrestamo(prestamo);

        return new ResponseEntity<>(prestamoActualizado, HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar préstamo",
            description = "Elimina un préstamo mediante su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Préstamo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable("id") Long id) {

        boolean eliminado = prestamoService.deletePrestamo(id);

        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}