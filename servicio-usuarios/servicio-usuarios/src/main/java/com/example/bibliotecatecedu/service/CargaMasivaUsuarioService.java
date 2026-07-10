package com.example.bibliotecatecedu.service;

import com.example.bibliotecatecedu.dto.UsuarioDto;
import com.example.bibliotecatecedu.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargaMasivaUsuarioService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void procesarCarga(List<UsuarioDto> listaDto) {

        int batchSize = 50;

        for (int i = 0; i < listaDto.size(); i++) {

            UsuarioDto dto = listaDto.get(i);

            Usuario usuario = new Usuario();
            usuario.setUsername(dto.getUsername());
            usuario.setNombre(dto.getNombre());
            usuario.setEmail(dto.getEmail());

            entityManager.persist(usuario);

            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        entityManager.flush();
        entityManager.clear();
    }
}