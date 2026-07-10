package com.example.bibliotecatecedu.dto;

import lombok.Data;

@Data
public class LibroDto {

    private String isbn;
    private String titulo;
    private String editorial;
    private int fechaPublicacion;
    private String autor;
}