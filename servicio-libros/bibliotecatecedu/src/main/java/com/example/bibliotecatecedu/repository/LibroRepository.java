package com.example.bibliotecatecedu.repository;

import com.example.bibliotecatecedu.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    Optional<Libro> findByIsbn(String isbn);
    
    List<Libro> findByAutorIgnoreCase(String autor);
    
    long countByFechaPublicacion(int anio);
   
    Optional<Libro> findFirstByOrderByFechaPublicacionAsc();
    
    Optional<Libro> findFirstByOrderByFechaPublicacionDesc();
  
    List<Libro> findAllByOrderByFechaPublicacionAsc();
}