package com.example.bibliotecatecedu.service;

import com.example.bibliotecatecedu.dto.LibroDto;
import com.example.bibliotecatecedu.model.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargaMasivaLibroService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void procesarCarga(List<LibroDto> listaDto) {

        int batchSize = 50;

        for (int i = 0; i < listaDto.size(); i++) {

            LibroDto dto = listaDto.get(i);

            Libro libro = new Libro();
            libro.setIsbn(dto.getIsbn());
            libro.setTitulo(dto.getTitulo());
            libro.setEditorial(dto.getEditorial());
            libro.setFechaPublicacion(dto.getFechaPublicacion());
            libro.setAutor(dto.getAutor());

            entityManager.persist(libro);

            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        entityManager.flush();
        entityManager.clear();
    }
}