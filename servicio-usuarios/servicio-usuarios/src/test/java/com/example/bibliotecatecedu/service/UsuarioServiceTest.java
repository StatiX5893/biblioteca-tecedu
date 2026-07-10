package com.example.bibliotecatecedu.service;

import com.example.bibliotecatecedu.model.Usuario;
import com.example.bibliotecatecedu.repository.UsuarioRepository;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testGetUsuarios() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("leo");
        usuario.setNombre("Leonardo");
        usuario.setEmail("leo@test.com");

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.getUsuarios();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("leo", resultado.get(0).getUsername());

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testSaveUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("leo");
        usuario.setNombre("Leonardo");
        usuario.setEmail("leo@test.com");

        Usuario guardado = new Usuario();
        guardado.setId(1L);
        guardado.setUsername("leo");
        guardado.setNombre("Leonardo");
        guardado.setEmail("leo@test.com");

        when(usuarioRepository.save(usuario)).thenReturn(guardado);

        Usuario resultado = usuarioService.saveUsuario(usuario);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Leonardo", resultado.getNombre());

        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testGetUsuarioIdExiste() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("leo");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.getUsuarioId(1L);

        assertNotNull(resultado);
        assertEquals("leo", resultado.getUsername());

        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUsuarioIdNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.getUsuarioId(99L);

        assertNull(resultado);

        verify(usuarioRepository, times(1)).findById(99L);
    }

    @Test
    void testGetUsuarioPorUsername() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("leo");

        when(usuarioRepository.findByUsername("leo")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.getUsuarioPorUsername("leo");

        assertNotNull(resultado);
        assertEquals("leo", resultado.getUsername());

        verify(usuarioRepository, times(1)).findByUsername("leo");
    }

    @Test
    void testDeleteUsuarioExiste() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        boolean resultado = usuarioService.deleteUsuario(1L);

        assertTrue(resultado);

        verify(usuarioRepository, times(1)).existsById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUsuarioNoExiste() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        boolean resultado = usuarioService.deleteUsuario(99L);

        assertFalse(resultado);

        verify(usuarioRepository, times(1)).existsById(99L);
        verify(usuarioRepository, never()).deleteById(99L);
    }
}