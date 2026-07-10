package com.example.bibliotecatecedu.service;

import com.example.bibliotecatecedu.model.Libro;
import com.example.bibliotecatecedu.repository.LibroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroService libroService;

    @Test
    void testGetLibros() {
        Libro libro = new Libro(1L, "978956000001", "Java Básico", "Duoc", 2024, "Autor Test");

        when(libroRepository.findAll()).thenReturn(List.of(libro));

        List<Libro> resultado = libroService.getLibros();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Java Básico", resultado.get(0).getTitulo());

        verify(libroRepository, times(1)).findAll();
    }

    @Test
    void testGetLibroIdExiste() {
        Libro libro = new Libro(1L, "978956000002", "Spring Boot", "Duoc", 2025, "Profesor Test");

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        Libro resultado = libroService.getLibroId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Spring Boot", resultado.getTitulo());

        verify(libroRepository, times(1)).findById(1L);
    }

    @Test
    void testGetLibroIdNoExiste() {
        when(libroRepository.findById(99L)).thenReturn(Optional.empty());

        Libro resultado = libroService.getLibroId(99L);

        assertNull(resultado);

        verify(libroRepository, times(1)).findById(99L);
    }

    @Test
    void testSaveLibro() {
        Libro libroSinId = new Libro(null, "978956000003", "Microservicios", "Duoc", 2026, "Autor Nuevo");
        Libro libroGuardado = new Libro(1L, "978956000003", "Microservicios", "Duoc", 2026, "Autor Nuevo");

        when(libroRepository.save(libroSinId)).thenReturn(libroGuardado);

        Libro resultado = libroService.saveLibro(libroSinId);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Microservicios", resultado.getTitulo());

        verify(libroRepository, times(1)).save(libroSinId);
    }

    @Test
    void testUpdateLibro() {
        Libro libro = new Libro(1L, "978956000004", "Libro Actualizado", "Duoc", 2026, "Autor Actualizado");

        when(libroRepository.save(libro)).thenReturn(libro);

        Libro resultado = libroService.updateLibro(libro);

        assertNotNull(resultado);
        assertEquals("Libro Actualizado", resultado.getTitulo());

        verify(libroRepository, times(1)).save(libro);
    }

    @Test
    void testDeleteLibroExiste() {
        when(libroRepository.existsById(1L)).thenReturn(true);

        String resultado = libroService.deleteLibro(1L);

        assertEquals("Libro eliminado con éxito", resultado);

        verify(libroRepository, times(1)).existsById(1L);
        verify(libroRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteLibroNoExiste() {
        when(libroRepository.existsById(99L)).thenReturn(false);

        String resultado = libroService.deleteLibro(99L);

        assertEquals("No se encontró el libro para eliminar", resultado);

        verify(libroRepository, times(1)).existsById(99L);
        verify(libroRepository, never()).deleteById(99L);
    }

    @Test
    void testGetLibroPorIsbn() {
        Libro libro = new Libro(1L, "978956000005", "Libro ISBN", "Duoc", 2024, "Autor ISBN");

        when(libroRepository.findByIsbn("978956000005")).thenReturn(Optional.of(libro));

        Libro resultado = libroService.getLibroPorIsbn("978956000005");

        assertNotNull(resultado);
        assertEquals("Libro ISBN", resultado.getTitulo());

        verify(libroRepository, times(1)).findByIsbn("978956000005");
    }

    @Test
    void testTotalLibrosV2() {
        when(libroRepository.count()).thenReturn(5L);

        int resultado = libroService.totalLibrosV2();

        assertEquals(5, resultado);

        verify(libroRepository, times(1)).count();
    }
}