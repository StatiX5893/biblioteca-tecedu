package com.example.bibliotecatecedu.controller;

import com.example.bibliotecatecedu.model.Prestamo;
import com.example.bibliotecatecedu.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Préstamos HATEOAS", description = "API v2 de préstamos con enlaces HATEOAS")
@RestController
@RequestMapping("/api/v2/prestamos")
public class PrestamoControllerV2 {

    @Autowired
    private PrestamoService prestamoService;

    @Operation(summary = "Listar préstamos con HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Prestamo>> listarPrestamos() {
        List<EntityModel<Prestamo>> prestamos = prestamoService.getPrestamos().stream()
                .map(prestamo -> EntityModel.of(prestamo,
                        linkTo(methodOn(PrestamoControllerV2.class).obtenerPrestamo(prestamo.getId())).withSelfRel(),
                        linkTo(methodOn(PrestamoControllerV2.class).listarPrestamos()).withRel("todos-los-prestamos"),
                        linkTo(methodOn(PrestamoControllerV2.class).obtenerPrestamosConLibros()).withRel("prestamos-con-libros"),
                        linkTo(methodOn(PrestamoControllerV2.class).obtenerDetallePrestamos()).withRel("detalle-prestamos")
                ))
                .toList();

        return CollectionModel.of(prestamos,
                linkTo(methodOn(PrestamoControllerV2.class).listarPrestamos()).withSelfRel());
    }

    @Operation(summary = "Buscar préstamo por ID con HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Prestamo>> obtenerPrestamo(@PathVariable Long id) {
        Prestamo prestamo = prestamoService.getPrestamoId(id);

        if (prestamo == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Prestamo> recurso = EntityModel.of(prestamo,
                linkTo(methodOn(PrestamoControllerV2.class).obtenerPrestamo(id)).withSelfRel(),
                linkTo(methodOn(PrestamoControllerV2.class).listarPrestamos()).withRel("todos-los-prestamos"),
                linkTo(methodOn(PrestamoControllerV2.class).eliminarPrestamo(id)).withRel("eliminar-prestamo")
        );

        return ResponseEntity.ok(recurso);
    }

    @Operation(summary = "Crear préstamo con HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Prestamo>> guardarPrestamo(@RequestBody Prestamo prestamo) {
        Prestamo nuevoPrestamo = prestamoService.savePrestamo(prestamo);

        if (nuevoPrestamo == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EntityModel<Prestamo> recurso = EntityModel.of(nuevoPrestamo,
                linkTo(methodOn(PrestamoControllerV2.class).obtenerPrestamo(nuevoPrestamo.getId())).withSelfRel(),
                linkTo(methodOn(PrestamoControllerV2.class).listarPrestamos()).withRel("todos-los-prestamos"),
                linkTo(methodOn(PrestamoControllerV2.class).obtenerPrestamosConLibros()).withRel("prestamos-con-libros")
        );

        return new ResponseEntity<>(recurso, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar préstamos con datos del libro")
    @GetMapping("/con-libros")
    public ResponseEntity<?> obtenerPrestamosConLibros() {
        return ResponseEntity.ok(prestamoService.getPrestamosConLibros());
    }

    @Operation(summary = "Listar detalle completo de préstamos")
    @GetMapping("/detalle")
    public ResponseEntity<?> obtenerDetallePrestamos() {
        return ResponseEntity.ok(prestamoService.getPrestamosDetalle());
    }

    @Operation(summary = "Eliminar préstamo con HATEOAS")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Long id) {
        boolean eliminado = prestamoService.deletePrestamo(id);

        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}