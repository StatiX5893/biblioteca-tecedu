package com.example.bibliotecatecedu.controller;

import com.example.bibliotecatecedu.model.Libro;
import com.example.bibliotecatecedu.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Libros HATEOAS", description = "API v2 de libros con enlaces HATEOAS")
@RestController
@RequestMapping("/api/v2/libros")
public class LibroControllerV2 {

    @Autowired
    private LibroService libroService;

    @Operation(summary = "Listar libros con HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Libro>> listarTodos() {
        List<EntityModel<Libro>> libros = libroService.getLibros().stream()
                .map(libro -> EntityModel.of(libro,
                        linkTo(methodOn(LibroControllerV2.class).obtenerPorId(libro.getId())).withSelfRel(),
                        linkTo(methodOn(LibroControllerV2.class).listarTodos()).withRel("todos-los-libros"),
                        linkTo(methodOn(LibroControllerV2.class).buscarPorAutor(libro.getAutor())).withRel("libros-del-autor")
                ))
                .toList();

        return CollectionModel.of(libros,
                linkTo(methodOn(LibroControllerV2.class).listarTodos()).withSelfRel());
    }

    @Operation(summary = "Buscar libro por ID con HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Libro>> obtenerPorId(@PathVariable Long id) {
        Libro libro = libroService.getLibroId(id);

        if (libro == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Libro> recurso = EntityModel.of(libro,
                linkTo(methodOn(LibroControllerV2.class).obtenerPorId(id)).withSelfRel(),
                linkTo(methodOn(LibroControllerV2.class).listarTodos()).withRel("todos-los-libros"),
                linkTo(methodOn(LibroControllerV2.class).buscarPorIsbn(libro.getIsbn())).withRel("buscar-por-isbn"),
                linkTo(methodOn(LibroControllerV2.class).eliminar(id)).withRel("eliminar-libro")
        );

        return ResponseEntity.ok(recurso);
    }

    @Operation(summary = "Crear libro con HATEOAS")
    @PostMapping
    public EntityModel<Libro> crear(@RequestBody Libro libro) {
        Libro nuevo = libroService.saveLibro(libro);

        return EntityModel.of(nuevo,
                linkTo(methodOn(LibroControllerV2.class).obtenerPorId(nuevo.getId())).withSelfRel(),
                linkTo(methodOn(LibroControllerV2.class).listarTodos()).withRel("todos-los-libros")
        );
    }

    @Operation(summary = "Buscar libros por autor con HATEOAS")
    @GetMapping("/autor/{autor}")
    public CollectionModel<EntityModel<Libro>> buscarPorAutor(@PathVariable String autor) {
        List<EntityModel<Libro>> libros = libroService.getLibroPorAutor(autor).stream()
                .map(libro -> EntityModel.of(libro,
                        linkTo(methodOn(LibroControllerV2.class).obtenerPorId(libro.getId())).withSelfRel(),
                        linkTo(methodOn(LibroControllerV2.class).listarTodos()).withRel("todos-los-libros")
                ))
                .toList();

        return CollectionModel.of(libros,
                linkTo(methodOn(LibroControllerV2.class).buscarPorAutor(autor)).withSelfRel());
    }

    @Operation(summary = "Buscar libro por ISBN con HATEOAS")
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<EntityModel<Libro>> buscarPorIsbn(@PathVariable String isbn) {
        Libro libro = libroService.getLibroPorIsbn(isbn);

        if (libro == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Libro> recurso = EntityModel.of(libro,
                linkTo(methodOn(LibroControllerV2.class).obtenerPorId(libro.getId())).withSelfRel(),
                linkTo(methodOn(LibroControllerV2.class).listarTodos()).withRel("todos-los-libros")
        );

        return ResponseEntity.ok(recurso);
    }

    @Operation(summary = "Eliminar libro con HATEOAS")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        String mensaje = libroService.deleteLibro(id);

        if (mensaje.contains("éxito")) {
            return ResponseEntity.ok(mensaje);
        }

        return ResponseEntity.status(404).body(mensaje);
    }
}