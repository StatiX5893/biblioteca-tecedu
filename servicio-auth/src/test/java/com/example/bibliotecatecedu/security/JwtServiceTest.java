package com.example.bibliotecatecedu.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void testGenerateTokenNoEsNull() {
        String token = jwtService.generateToken("admin");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generateToken("admin");

        String username = jwtService.extractUsername(token);

        assertEquals("admin", username);
    }

    @Test
    void testTokenTieneFormatoJwt() {
        String token = jwtService.generateToken("admin");

        String[] partes = token.split("\\.");

        assertEquals(3, partes.length);
    }
}