package com.example.bibliotecatecedu.service;

import com.example.bibliotecatecedu.model.Role;
import com.example.bibliotecatecedu.model.User;
import com.example.bibliotecatecedu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testFindByUsernameExiste() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("1234");
        user.setRole(Role.ROLE_ADMIN);

        when(repository.findByUsername("admin")).thenReturn(Optional.of(user));

        User resultado = userService.findByUsername("admin");

        assertNotNull(resultado);
        assertEquals("admin", resultado.getUsername());
        assertEquals(Role.ROLE_ADMIN, resultado.getRole());

        verify(repository, times(1)).findByUsername("admin");
    }

    @Test
    void testFindByUsernameOrNullExiste() {
        User user = new User();
        user.setId(1L);
        user.setUsername("leo");
        user.setPassword("1234");
        user.setRole(Role.ROLE_USER);

        when(repository.findByUsername("leo")).thenReturn(Optional.of(user));

        User resultado = userService.findByUsernameOrNull("leo");

        assertNotNull(resultado);
        assertEquals("leo", resultado.getUsername());
        assertEquals(Role.ROLE_USER, resultado.getRole());

        verify(repository, times(1)).findByUsername("leo");
    }

    @Test
    void testFindByUsernameOrNullNoExiste() {
        when(repository.findByUsername("noexiste")).thenReturn(Optional.empty());

        User resultado = userService.findByUsernameOrNull("noexiste");

        assertNull(resultado);

        verify(repository, times(1)).findByUsername("noexiste");
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");
        user.setRole(Role.ROLE_ADMIN);

        User userGuardado = new User();
        userGuardado.setId(1L);
        userGuardado.setUsername("admin");
        userGuardado.setPassword("passwordEncriptada");
        userGuardado.setRole(Role.ROLE_ADMIN);

        when(passwordEncoder.encode("1234")).thenReturn("passwordEncriptada");
        when(repository.save(user)).thenReturn(userGuardado);

        User resultado = userService.save(user);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("admin", resultado.getUsername());
        assertEquals("passwordEncriptada", resultado.getPassword());
        assertEquals(Role.ROLE_ADMIN, resultado.getRole());

        verify(passwordEncoder, times(1)).encode("1234");
        verify(repository, times(1)).save(user);
    }
}