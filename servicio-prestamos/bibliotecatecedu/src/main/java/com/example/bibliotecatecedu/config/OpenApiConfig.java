package com.example.bibliotecatecedu.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Servicio Préstamos API",
                version = "1.0",
                description = "API para la gestión de préstamos del sistema Biblioteca TecEdu"
        )
)
public class OpenApiConfig {
}