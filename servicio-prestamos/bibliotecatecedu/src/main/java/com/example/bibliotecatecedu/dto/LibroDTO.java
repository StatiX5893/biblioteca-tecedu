package com.example.bibliotecatecedu.dto;

import lombok.Data;

@Data
public class LibroDTO {
    private Long id;
    private String isbn;
    private String titulo;
    private String editorial;
    private int fechaPublicacion;
    private String autor;
}