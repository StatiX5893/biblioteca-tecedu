package com.example.bibliotecatecedu.service;

import com.example.bibliotecatecedu.client.LibroClient;
import com.example.bibliotecatecedu.client.UsuarioClient;
import com.example.bibliotecatecedu.dto.LibroDTO;
import com.example.bibliotecatecedu.dto.UsuarioDTO;
import com.example.bibliotecatecedu.model.Prestamo;
import com.example.bibliotecatecedu.repository.PrestamoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private LibroClient libroClient;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private PrestamoService prestamoService;

    @Test
    void testGetPrestamos() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setIdLibro(10L);
        prestamo.setRunSolicitante("leo");
        prestamo.setFechaSolicitud(new Date());
        prestamo.setCantidadDias(7);
        prestamo.setMultas(0);

        when(prestamoRepository.findAll()).thenReturn(List.of(prestamo));

        List<Prestamo> resultado = prestamoService.getPrestamos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getIdLibro());

        verify(prestamoRepository, times(1)).findAll();
    }

    @Test
    void testGetPrestamoIdExiste() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setIdLibro(10L);

        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));

        Prestamo resultado = prestamoService.getPrestamoId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(10L, resultado.getIdLibro());

        verify(prestamoRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPrestamoIdNoExiste() {
        when(prestamoRepository.findById(99L)).thenReturn(Optional.empty());

        Prestamo resultado = prestamoService.getPrestamoId(99L);

        assertNull(resultado);

        verify(prestamoRepository, times(1)).findById(99L);
    }

    @Test
    void testSavePrestamoCuandoLibroExiste() {
        Prestamo prestamo = new Prestamo();
        prestamo.setIdLibro(10L);
        prestamo.setRunSolicitante("leo");
        prestamo.setFechaSolicitud(new Date());
        prestamo.setCantidadDias(7);
        prestamo.setMultas(0);

        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(10L);
        libroDTO.setTitulo("Java Básico");

        Prestamo prestamoGuardado = new Prestamo();
        prestamoGuardado.setId(1L);
        prestamoGuardado.setIdLibro(10L);
        prestamoGuardado.setRunSolicitante("leo");
        prestamoGuardado.setCantidadDias(7);
        prestamoGuardado.setMultas(0);

        when(libroClient.obtenerLibroPorId(10L)).thenReturn(libroDTO);
        when(prestamoRepository.save(prestamo)).thenReturn(prestamoGuardado);

        Prestamo resultado = prestamoService.savePrestamo(prestamo);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(10L, resultado.getIdLibro());

        verify(libroClient, times(1)).obtenerLibroPorId(10L);
        verify(prestamoRepository, times(1)).save(prestamo);
    }

    @Test
    void testSavePrestamoCuandoLibroNoExiste() {
        Prestamo prestamo = new Prestamo();
        prestamo.setIdLibro(99L);

        when(libroClient.obtenerLibroPorId(99L)).thenThrow(new RuntimeException("Libro no encontrado"));

        Prestamo resultado = prestamoService.savePrestamo(prestamo);

        assertNull(resultado);

        verify(libroClient, times(1)).obtenerLibroPorId(99L);
        verify(prestamoRepository, never()).save(prestamo);
    }

    @Test
    void testUpdatePrestamo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setIdLibro(10L);
        prestamo.setCantidadDias(5);

        when(prestamoRepository.save(prestamo)).thenReturn(prestamo);

        Prestamo resultado = prestamoService.updatePrestamo(prestamo);

        assertNotNull(resultado);
        assertEquals(5, resultado.getCantidadDias());

        verify(prestamoRepository, times(1)).save(prestamo);
    }

    @Test
    void testDeletePrestamoExiste() {
        when(prestamoRepository.existsById(1L)).thenReturn(true);

        boolean resultado = prestamoService.deletePrestamo(1L);

        assertTrue(resultado);

        verify(prestamoRepository, times(1)).existsById(1L);
        verify(prestamoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePrestamoNoExiste() {
        when(prestamoRepository.existsById(99L)).thenReturn(false);

        boolean resultado = prestamoService.deletePrestamo(99L);

        assertFalse(resultado);

        verify(prestamoRepository, times(1)).existsById(99L);
        verify(prestamoRepository, never()).deleteById(99L);
    }

    @Test
    void testGetPrestamosConLibros() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setIdLibro(10L);

        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(10L);
        libroDTO.setTitulo("Spring Boot");

        when(prestamoRepository.findAll()).thenReturn(List.of(prestamo));
        when(libroClient.obtenerLibroPorId(10L)).thenReturn(libroDTO);

        List<Map<String, Object>> resultado = prestamoService.getPrestamosConLibros();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).containsKey("prestamo"));
        assertTrue(resultado.get(0).containsKey("libro"));

        verify(prestamoRepository, times(1)).findAll();
        verify(libroClient, times(1)).obtenerLibroPorId(10L);
    }

    @Test
    void testGetPrestamosDetalle() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setIdLibro(10L);
        prestamo.setRunSolicitante("leo");

        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(10L);
        libroDTO.setTitulo("Spring Boot");

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setUsername("leo");
        usuarioDTO.setNombre("Leonardo");
        usuarioDTO.setEmail("leo@test.com");

        when(prestamoRepository.findAll()).thenReturn(List.of(prestamo));
        when(libroClient.obtenerLibroPorId(10L)).thenReturn(libroDTO);
        when(usuarioClient.obtenerUsuarioPorUsername("leo")).thenReturn(usuarioDTO);

        List<Map<String, Object>> resultado = prestamoService.getPrestamosDetalle();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).containsKey("prestamo"));
        assertTrue(resultado.get(0).containsKey("libro"));
        assertTrue(resultado.get(0).containsKey("usuario"));

        verify(prestamoRepository, times(1)).findAll();
        verify(libroClient, times(1)).obtenerLibroPorId(10L);
        verify(usuarioClient, times(1)).obtenerUsuarioPorUsername("leo");
    }
}