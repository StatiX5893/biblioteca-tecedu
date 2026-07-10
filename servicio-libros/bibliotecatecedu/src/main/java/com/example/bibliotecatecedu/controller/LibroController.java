package com.example.bibliotecatecedu.controller;

import com.example.bibliotecatecedu.model.Libro;
import com.example.bibliotecatecedu.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Libros", description = "API para la gestión de libros")
@RestController
@RequestMapping("/api/v1/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Operation(
            summary = "Listar todos los libros",
            description = "Obtiene todos los libros registrados en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public List<Libro> listarTodos() {
        return libroService.getLibros();
    }

    @Operation(
            summary = "Buscar libro por ID",
            description = "Obtiene un libro específico mediante su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerPorId(@PathVariable("id") Long id) {
        Libro libro = libroService.getLibroId(id);
        return (libro != null) ? ResponseEntity.ok(libro) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Crear libro",
            description = "Registra un nuevo libro en la base de datos"
    )
    @ApiResponse(responseCode = "200", description = "Libro creado correctamente")
    @PostMapping
    public Libro crear(@RequestBody Libro libro) {
        return libroService.saveLibro(libro);
    }

    @Operation(
            summary = "Actualizar libro",
            description = "Actualiza los datos de un libro existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(
            @PathVariable("id") Long id,
            @RequestBody Libro libroDetalles) {

        Libro existente = libroService.getLibroId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        libroDetalles.setId(id);
        Libro actualizado = libroService.updateLibro(libroDetalles);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(
            summary = "Eliminar libro",
            description = "Elimina un libro mediante su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Long id) {
        String mensaje = libroService.deleteLibro(id);

        if (mensaje.contains("éxito")) {
            return ResponseEntity.ok(mensaje);
        }

        return ResponseEntity.status(404).body(mensaje);
    }

    @Operation(
            summary = "Buscar libros por autor",
            description = "Obtiene una lista de libros filtrados por nombre del autor"
    )
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    @GetMapping("/autor/{autor}")
    public List<Libro> porAutor(@PathVariable("autor") String autor) {
        return libroService.getLibroPorAutor(autor);
    }

    @Operation(
            summary = "Buscar libro más reciente",
            description = "Obtiene el libro con el año de publicación más reciente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "No existen libros registrados")
    })
    @GetMapping("/reciente")
    public ResponseEntity<Libro> elMasNuevo() {
        Libro libro = libroService.getLibroMasNuevo();
        return (libro != null) ? ResponseEntity.ok(libro) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Buscar libro por ISBN",
            description = "Obtiene un libro mediante su código ISBN"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Libro> porIsbn(@PathVariable String isbn) {
        Libro libro = libroService.getLibroPorIsbn(isbn);
        return (libro != null) ? ResponseEntity.ok(libro) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Contar libros por año",
            description = "Obtiene la cantidad de libros publicados en un año específico"
    )
    @ApiResponse(responseCode = "200", description = "Cantidad obtenida correctamente")
    @GetMapping("/cantidad/{anio}")
    public int cantidadPorAnio(@PathVariable int anio) {
        return libroService.getCantidadLibrosPorAnio(anio);
    }

    @Operation(
            summary = "Buscar libro más antiguo",
            description = "Obtiene el libro con el año de publicación más antiguo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "No existen libros registrados")
    })
    @GetMapping("/antiguo")
    public ResponseEntity<Libro> elMasAntiguo() {
        Libro libro = libroService.getLibroMasAntiguo();
        return (libro != null) ? ResponseEntity.ok(libro) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Listar libros ordenados",
            description = "Obtiene todos los libros ordenados por año de publicación ascendente"
    )
    @ApiResponse(responseCode = "200", description = "Listado ordenado correctamente")
    @GetMapping("/ordenados")
    public List<Libro> ordenadosPorAnio() {
        return libroService.getLibrosOrdenados();
    }

    @Operation(
            summary = "Obtener total de libros",
            description = "Retorna la cantidad total de libros registrados"
    )
    @ApiResponse(responseCode = "200", description = "Total obtenido correctamente")
    @GetMapping("/total")
    public int totalLibros() {
        return libroService.totalLibrosV2();
    }
}