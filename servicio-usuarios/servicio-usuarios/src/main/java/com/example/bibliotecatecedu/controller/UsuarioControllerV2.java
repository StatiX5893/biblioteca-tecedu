package com.example.bibliotecatecedu.controller;

import com.example.bibliotecatecedu.model.Usuario;
import com.example.bibliotecatecedu.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Usuarios HATEOAS", description = "API v2 de usuarios con enlaces HATEOAS")
@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar usuarios con HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Usuario>> listarUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.getUsuarios().stream()
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuario(usuario.getId())).withSelfRel(),
                        linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("todos-los-usuarios"),
                        linkTo(methodOn(UsuarioControllerV2.class).obtenerPorUsername(usuario.getUsername())).withRel("buscar-por-username")
                ))
                .toList();

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withSelfRel());
    }

    @Operation(summary = "Buscar usuario por ID con HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioId(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Usuario> recurso = EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuario(id)).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("todos-los-usuarios"),
                linkTo(methodOn(UsuarioControllerV2.class).obtenerPorUsername(usuario.getUsername())).withRel("buscar-por-username"),
                linkTo(methodOn(UsuarioControllerV2.class).eliminarUsuario(id)).withRel("eliminar-usuario")
        );

        return ResponseEntity.ok(recurso);
    }

    @Operation(summary = "Crear usuario con HATEOAS")
    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> guardarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.saveUsuario(usuario);

        EntityModel<Usuario> recurso = EntityModel.of(nuevo,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuario(nuevo.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("todos-los-usuarios")
        );

        return new ResponseEntity<>(recurso, HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar usuario por username con HATEOAS")
    @GetMapping("/username/{username}")
    public ResponseEntity<EntityModel<Usuario>> obtenerPorUsername(@PathVariable String username) {
        Usuario usuario = usuarioService.getUsuarioPorUsername(username);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Usuario> recurso = EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuario(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("todos-los-usuarios")
        );

        return ResponseEntity.ok(recurso);
    }

    @Operation(summary = "Eliminar usuario con HATEOAS")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = usuarioService.deleteUsuario(id);

        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}