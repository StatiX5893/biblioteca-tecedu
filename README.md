# Biblioteca TecEdu

Biblioteca TecEdu es un sistema desarrollado con arquitectura de microservicios para gestionar libros, usuarios, préstamos y autenticación.

## Integrante

- Leonardo Bastidas

## Arquitectura del proyecto

El sistema está compuesto por los siguientes servicios:

- API Gateway
- Servicio de autenticación
- Servicio de libros
- Servicio de préstamos
- Servicio de usuarios

## Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Cloud Gateway
- Spring Security
- JWT
- Spring Data JPA
- MySQL
- Flyway
- OpenFeign
- Swagger / OpenAPI
- JUnit
- Mockito
- Maven
- Postman

## Puertos

| Servicio | Puerto |
|---|---:|
| API Gateway | 8080 |
| Servicio de libros | 8081 |
| Servicio de préstamos | 8082 |
| Servicio de usuarios | 8083 |
| Servicio de autenticación | 8084 |

## Rutas principales del Gateway

| Recurso | Ruta |
|---|---|
| Libros | `/api/v1/libros/**` |
| Préstamos | `/api/v1/prestamos/**` |
| Usuarios | `/api/v1/usuarios/**` |
| Autenticación | `/api/v1/auth/**` |
| Administración | `/api/v1/admin/**` |

## Bases de datos

- `biblioteca_db`
- `prestamos_db`
- `usuarios_db`
- `auth_db`

## Ejecución local

1. Iniciar MySQL.
2. Verificar la creación de las bases de datos.
3. Ejecutar el servicio de autenticación.
4. Ejecutar el servicio de usuarios.
5. Ejecutar el servicio de libros.
6. Ejecutar el servicio de préstamos.
7. Ejecutar el API Gateway.
8. Probar los endpoints con Postman o Swagger.

## Swagger

La documentación Swagger se encuentra disponible en:

```text
http://localhost:8081/swagger-ui/index.html
http://localhost:8082/swagger-ui/index.html
http://localhost:8083/swagger-ui/index.html
http://localhost:8084/swagger-ui/index.html
