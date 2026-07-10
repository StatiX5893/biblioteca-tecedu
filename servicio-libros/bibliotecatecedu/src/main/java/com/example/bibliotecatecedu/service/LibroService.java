package com.example.bibliotecatecedu.service;

import com.example.bibliotecatecedu.model.Libro;
import com.example.bibliotecatecedu.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;
    
    @Transactional(readOnly = true)
    public List<Libro> getLibros() {
        return libroRepository.findAll();
    }
    
    @Transactional 
    public Libro saveLibro(Libro libro) {
        return libroRepository.save(libro); 
    }
    
    @Transactional(readOnly = true)
    public Libro getLibroId(Long id) {
        return libroRepository.findById(id).orElse(null);
    }
    
    @Transactional 
    public Libro updateLibro(Libro libro) {
        return libroRepository.save(libro);
    }
    
    @Transactional
    public String deleteLibro(Long id) {
        if (libroRepository.existsById(id)) {
            libroRepository.deleteById(id);
            return "Libro eliminado con éxito";
        }
        return "No se encontró el libro para eliminar";
    }    
   
    @Transactional(readOnly = true)
    public int totalLibrosV2() {
        return (int) libroRepository.count();
    }
    
    @Transactional(readOnly = true)
    public Libro getLibroPorIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn).orElse(null);
    }
    
    @Transactional(readOnly = true)
    public int getCantidadLibrosPorAnio(int anio) {
        return (int) libroRepository.countByFechaPublicacion(anio);
    }
    
    @Transactional(readOnly = true)
    public List<Libro> getLibroPorAutor(String autor) {
        return libroRepository.findByAutorIgnoreCase(autor);
    }
    
    @Transactional(readOnly = true)
    public Libro getLibroMasAntiguo() {
        return libroRepository.findFirstByOrderByFechaPublicacionAsc().orElse(null);
    }
    
    @Transactional(readOnly = true)
    public Libro getLibroMasNuevo() {
        return libroRepository.findFirstByOrderByFechaPublicacionDesc().orElse(null);
    }
    
    @Transactional(readOnly = true)
    public List<Libro> getLibrosOrdenados() {
        return libroRepository.findAllByOrderByFechaPublicacionAsc();
    }
}