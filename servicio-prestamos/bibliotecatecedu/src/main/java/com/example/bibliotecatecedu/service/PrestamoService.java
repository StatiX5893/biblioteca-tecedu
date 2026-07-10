package com.example.bibliotecatecedu.service;

import com.example.bibliotecatecedu.client.LibroClient;
import com.example.bibliotecatecedu.client.UsuarioClient;
import com.example.bibliotecatecedu.dto.LibroDTO;
import com.example.bibliotecatecedu.dto.UsuarioDTO;
import com.example.bibliotecatecedu.model.Prestamo;
import com.example.bibliotecatecedu.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroClient libroClient;

    @Autowired
    private UsuarioClient usuarioClient;

    @Transactional(readOnly = true)
    public List<Prestamo> getPrestamos() {
        return prestamoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Prestamo getPrestamoId(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Prestamo updatePrestamo(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public boolean deletePrestamo(Long id) {
        if (prestamoRepository.existsById(id)) {
            prestamoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Prestamo savePrestamo(Prestamo prestamo) {
        try {
            LibroDTO libroExiste = libroClient.obtenerLibroPorId(prestamo.getIdLibro());

            if (libroExiste == null) {
                return null;
            }

            System.out.println("Libro validado con OpenFeign: " + libroExiste.getTitulo());

            if (prestamo.getMultas() == 0) {
                prestamo.setMultas(0);
            }

            return prestamoRepository.save(prestamo);

        } catch (Exception e) {
            System.out.println("Error: el libro con ID " + prestamo.getIdLibro() + " no existe o servicio-libros está caído.");
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getPrestamosConLibros() {
        List<Prestamo> prestamos = prestamoRepository.findAll();

        return prestamos.stream().map(prestamo -> {
            Object libro;

            try {
                libro = libroClient.obtenerLibroPorId(prestamo.getIdLibro());
            } catch (Exception e) {
                libro = "No se pudo obtener información del libro";
            }

            return Map.<String, Object>of(
                    "prestamo", prestamo,
                    "libro", libro
            );
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getPrestamosDetalle() {
        List<Prestamo> prestamos = prestamoRepository.findAll();

        return prestamos.stream().map(prestamo -> {
            Object libro;
            Object usuario;

            try {
                libro = libroClient.obtenerLibroPorId(prestamo.getIdLibro());
            } catch (Exception e) {
                libro = "No se pudo obtener información del libro";
            }

            try {
                usuario = usuarioClient.obtenerUsuarioPorUsername(prestamo.getRunSolicitante());
            } catch (Exception e) {
                usuario = "No se pudo obtener información del usuario";
            }

            return Map.<String, Object>of(
                    "prestamo", prestamo,
                    "libro", libro,
                    "usuario", usuario
            );
        }).toList();
    }
}