package com.example.bibliotecatecedu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Administración", description = "Endpoints protegidos para usuarios con rol ADMIN")
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Operation(
            summary = "Probar acceso administrativo",
            description = "Endpoint protegido que solo permite acceso a usuarios con rol ADMIN"
    )
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/test")
    public String admin() {
        return "Acceso solo ADMIN";
    }
}