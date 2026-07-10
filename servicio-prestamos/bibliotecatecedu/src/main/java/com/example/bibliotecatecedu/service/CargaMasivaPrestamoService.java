package com.example.bibliotecatecedu.service;

import com.example.bibliotecatecedu.dto.PrestamoDto;
import com.example.bibliotecatecedu.model.Prestamo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargaMasivaPrestamoService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void procesarCarga(List<PrestamoDto> listaDto) {
        int batchSize = 50;

        for (int i = 0; i < listaDto.size(); i++) {
            PrestamoDto dto = listaDto.get(i);

            Prestamo prestamo = new Prestamo();
            prestamo.setIdLibro(dto.getIdLibro());
            prestamo.setRunSolicitante(dto.getRunSolicitante());
            prestamo.setFechaSolicitud(dto.getFechaSolicitud());
            prestamo.setFechaEntrega(dto.getFechaEntrega());
            prestamo.setCantidadDias(dto.getCantidadDias());
            prestamo.setMultas(dto.getMultas());

            entityManager.persist(prestamo);

            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        entityManager.flush();
        entityManager.clear();
    }
}